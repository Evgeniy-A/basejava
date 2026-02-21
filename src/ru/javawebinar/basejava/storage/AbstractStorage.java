package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract SK findSearchKey(String uuid);

    protected abstract boolean isExist(SK searchKey);

    protected abstract void replaceResume(Resume r, SK searchKey);

    protected abstract void addResume(Resume r, SK searchKey);

    protected abstract void removeResume(SK searchKey);

    protected abstract Resume getResume(SK searchKey);

    protected abstract List<Resume> getResumesAsList();

    @Override
    public final void update(Resume r) {
        LOG.info("Update " + r);
        SK searchKey = getExistingSearchKey(r.getUuid());
        replaceResume(r, searchKey);
    }

    @Override
    public final void save(Resume r) {
        LOG.info("Save " + r);
        SK searchKey = getNotExistingSearchKey(r.getUuid());
        addResume(r, searchKey);
    }

    @Override
    public final void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK searchKey = getExistingSearchKey(uuid);
        removeResume(searchKey);
    }

    @Override
    public final Resume get(String uuid) {
        LOG.info("Get" + uuid);
        SK searchKey = getExistingSearchKey(uuid);
        return getResume(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted ");
        List<Resume> resumesAsList = getResumesAsList();
        Collections.sort(resumesAsList);
        return resumesAsList;
    }

    private SK getExistingSearchKey(String uuid) {
        LOG.info("GetExistingSearchKey " + uuid);
        SK searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Резюме с uuid: " + uuid + " нет в базе");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistingSearchKey(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Резюме с uuid: " + uuid + " уже есть в базе");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}