package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected abstract Object findSearchKey(String uuid);

    protected abstract void checkResumeExists(Object searchKey, String uuid);

    protected abstract void replaceResume(Resume r, Object searchKey);

    protected abstract void checkResumeNotExists(Object searchKey, String uuid);

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