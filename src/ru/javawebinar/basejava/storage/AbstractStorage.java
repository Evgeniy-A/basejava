package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected abstract Object findSearchKey(String uuid);

    protected abstract boolean hasKey(Object searchKey);

    protected abstract void replaceResume(Resume r, Object searchKey);

    protected abstract void addResume(Resume r, Object searchKey);

    protected abstract void removeResume(Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    public final void update(Resume r) {
        Object searchKey = getExistingSearchKey(r.getUuid());
        replaceResume(r, searchKey);
    }

    public final void save(Resume r) {
        Object searchKey = getNotExistingSearchKey(r.getUuid());
        addResume(r, searchKey);
    }

    public final void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        removeResume(searchKey);
    }

    public final Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    public final void checkResumeExists(Object searchKey, String uuid) {
        if (!hasKey(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
    }

    public final void checkResumeNotExists(Object searchKey, String uuid) {
        if (hasKey(searchKey)) {
            throw new ExistStorageException(uuid);
        }
    }

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        checkResumeExists(searchKey, uuid);
        return searchKey;
    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        checkResumeNotExists(searchKey, uuid);
        return searchKey;
    }
}