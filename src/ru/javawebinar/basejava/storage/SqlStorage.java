package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;
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
            try (PreparedStatement pst = conn.prepareStatement(
                    "UPDATE contact SET value = ? WHERE resume_uuid = ? AND type = ?")) {
                for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                    pst.setString(1, e.getValue());
                    pst.setString(2, r.getUuid());
                    pst.setString(3, e.getKey().name());
                    pst.addBatch();
                }
                pst.executeBatch();
            }
            return null;
        });
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
            try (PreparedStatement pst = conn.prepareStatement(
                    "INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
                for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                    pst.setString(1, r.getUuid());
                    pst.setString(2, e.getKey().name());
                    pst.setString(3, e.getValue());
                    pst.addBatch();
                }
                pst.executeBatch();
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.executeSql("" +
                                    " SELECT * FROM resume r " +
                                    " LEFT JOIN contact c " +
                                    " ON r.uuid = c.resume_uuid " +
                                    " WHERE r.uuid =?",
                pst -> {
                    pst.setString(1, uuid);
                    ResultSet rs = pst.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = Resume.of(uuid, rs.getString("full_name"));
                    do {
                        String value = rs.getString("value");
                        if (value != null) {
                            ContactType type = ContactType.valueOf(rs.getString("type"));
                            r.setContacts(type, value);
                        }
                    } while (rs.next());
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

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted");
        return sqlHelper.executeSql("" +
                                    " SELECT * FROM resume r " +
                                    " LEFT JOIN contact c " +
                                    " ON r.uuid = c.resume_uuid " +
                                    " ORDER BY r.full_name, r.uuid",
                pst -> {
                    ResultSet rs = pst.executeQuery();
                    Map<String, Resume> map = new LinkedHashMap<>();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid");
                        String fullName = rs.getString("full_name");
                        Resume r = map.computeIfAbsent(uuid,
                                id -> Resume.of(uuid, fullName));
                        String value = rs.getString("value");
                        if (value != null) {
                            ContactType type = ContactType.valueOf(rs.getString("type"));
                            r.setContacts(type, value);
                        }
                    }
                    return new ArrayList<>(map.values());
                });
    }

    @Override
    public int size() {
        LOG.info("Size");
        return sqlHelper.executeSql("SELECT COUNT(*) FROM  resume", pst -> {
            ResultSet rs = pst.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }
}