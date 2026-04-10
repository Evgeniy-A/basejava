package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
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
        sqlHelper.runSQL("DELETE FROM resume");
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        sqlHelper.runSQL("UPDATE resume  SET full_name = ? WHERE uuid = ?", pst -> {
            pst.setString(1, r.getFullName());
            pst.setString(2, r.getUuid());
            if (pst.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        sqlHelper.runSQL("INSERT INTO resume (uuid, full_name) VALUES (?, ?)", pst -> {
            pst.setString(1, r.getUuid());
            pst.setString(2, r.getFullName());
            pst.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.runSQL("SELECT * FROM resume r WHERE r.uuid =?", pst -> {
            pst.setString(1, uuid);
            ResultSet rs = pst.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return Resume.of(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.runSQL("DELETE FROM resume WHERE uuid = ?", pst -> {
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
        return sqlHelper.runSQL("SELECT * FROM resume r ORDER BY full_name, uuid", pst -> {
            ResultSet rs = pst.executeQuery();
            List<Resume> allResumes = new ArrayList<>();
            while (rs.next()){
                allResumes.add(Resume.of(rs.getString("uuid"), rs.getString("full_name")));
            }
            return allResumes;
        });
    }

    @Override
    public int size() {
        LOG.info("Size");
        return sqlHelper.runSQL("SELECT count(*) FROM  resume", pst -> {
        ResultSet rs = pst.executeQuery();
        return rs.next() ? rs.getInt(1) : 0;
        });
    }
}