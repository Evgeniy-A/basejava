package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {
    protected abstract SK findSearchKey(String uuid);

    protected abstract boolean isExist(SK searchKey);

    protected abstract void replaceResume(Resume r, SK searchKey);

    protected abstract void addResume(Resume r, SK searchKey);

    protected abstract void removeResume(SK searchKey);

    protected abstract Resume getResume(SK searchKey);

    protected abstract List<Resume> getResumesAsList();

    @Override
    public final void update(Resume r) {
        SK searchKey = getExistingSearchKey(r.getUuid());
        replaceResume(r, searchKey);
    }

    @Override
    public final void save(Resume r) {
        SK searchKey = getNotExistingSearchKey(r.getUuid());
        addResume(r, searchKey);
    }

    @Override
    public final void delete(String uuid) {
        SK searchKey = getExistingSearchKey(uuid);
        removeResume(searchKey);
    }

    @Override
    public final Resume get(String uuid) {
        SK searchKey = getExistingSearchKey(uuid);
        return getResume(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumesAsList = getResumesAsList();
        Collections.sort(resumesAsList);
        return resumesAsList;
    }

    private SK getExistingSearchKey(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistingSearchKey(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}