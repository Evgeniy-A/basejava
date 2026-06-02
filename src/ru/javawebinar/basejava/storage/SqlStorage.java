package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SqlHelper;
import ru.javawebinar.basejava.util.JsonParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        LOG.info("Clear");
        sqlHelper.executeSql("DELETE FROM resume");
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        sqlHelper.transactionExecuteSql(conn -> {
            try (PreparedStatement pst = conn.prepareStatement(
                    "UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                pst.setString(1, r.getFullName());
                pst.setString(2, r.getUuid());
                if (pst.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteAttributes(conn, r, "DELETE FROM contact WHERE resume_uuid = ?");
            deleteAttributes(conn, r, "DELETE FROM section WHERE resume_uuid = ?");
            insertContacts(conn, r);
            insertSections(conn, r);
            return null;
        });
    }

    private void deleteAttributes(Connection conn, Resume r, String sql) throws SQLException {
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, r.getUuid());
            pst.execute();
        }
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        sqlHelper.transactionExecuteSql(conn -> {
            try (PreparedStatement pst = conn.prepareStatement(
                    "INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                pst.setString(1, r.getUuid());
                pst.setString(2, r.getFullName());
                pst.execute();
            }
            insertContacts(conn, r);
            insertSections(conn, r);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.transactionExecuteSql(conn -> {
            Resume r;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                r = Resume.of(uuid, rs.getString("full_name"));
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(rs, r);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(rs, r);
                }
            }
            return r;
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.executeSql("DELETE FROM resume WHERE uuid = ?", pst -> {
            pst.setString(1, uuid);
            if (pst.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted");
        Map<String, Resume> map = new LinkedHashMap<>();
        return sqlHelper.transactionExecuteSql(conn -> {
            try (PreparedStatement pst = conn.prepareStatement(
                    """
                            SELECT * FROM resume r
                            ORDER BY r.full_name, r.uuid
                            """)) {
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    String fullName = rs.getString("full_name");
                    map.put(uuid, Resume.of(uuid, fullName));
                }
            }
            try (PreparedStatement pst = conn.prepareStatement(
                    """
                            SELECT * FROM contact c
                            """)) {
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    Resume r = map.get(uuid);
                    addContact(rs, r);
                }
            }
            try (PreparedStatement pst = conn.prepareStatement(
                    """
                            SELECT * FROM section s
                            """)) {
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    Resume r = map.get(uuid);
                    addSection(rs, r);
                }
            }
            return new ArrayList<>(map.values());
        });
    }

    @Override
    public int size() {
        LOG.info("Size");
        return sqlHelper.executeSql("SELECT COUNT(*) FROM resume", pst -> {
            ResultSet rs = pst.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            ContactType type = ContactType.valueOf(rs.getString("type"));
            r.setContacts(type, value);
        }
    }

    private void addSection(ResultSet rs, Resume r) throws SQLException {
        String content = rs.getString("content");
        if (content != null) {
            SectionType type = SectionType.valueOf(rs.getString("type"));
            r.setSections(type, deserializeSection(type, content));
        }
    }

    private void insertContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement pst = conn.prepareStatement(
                "INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> entry : r.getContacts().entrySet()) {
                pst.setString(1, r.getUuid());
                pst.setString(2, entry.getKey().name());
                pst.setString(3, entry.getValue());
                pst.addBatch();
            }
            pst.executeBatch();
        }
    }

    private void insertSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement pst = conn.prepareStatement(
                "INSERT INTO section (resume_uuid, type, content) VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, AbstractSection> entry : r.getSections().entrySet()) {
                pst.setString(1, r.getUuid());
                pst.setString(2, entry.getKey().name());
                AbstractSection section = entry.getValue();
                pst.setString(3, serializeSection(section));
                pst.addBatch();
            }
            pst.executeBatch();
        }
    }

    private String serializeSection(AbstractSection section) {
        if (section instanceof TextSection textSection) {
            return textSection.getContent();
        }
        if (section instanceof ListSection listSection) {
            return String.join("\n", listSection.getContentList());
        }
        return JsonParser.write(section, AbstractSection.class);
    }

    private AbstractSection deserializeSection(SectionType type, String content) {
        if (type == SectionType.OBJECTIVE || type == SectionType.PERSONAL) {
            return new TextSection(content);
        }
        if (type == SectionType.ACHIEVEMENT || type == SectionType.QUALIFICATIONS) {
            return new ListSection(content.split("\\n"));
        }
        return JsonParser.read(content, AbstractSection.class);
    }
}