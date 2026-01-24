package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    protected Integer findSearchKey(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected void checkResumeExists(Object searchKey, String uuid) {
        if (searchKey == null) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    protected void replaceResume(Resume r, Object searchKey) {
        int index = (int) searchKey;
        storage.set(index, r);
    }

    @Override
    protected void checkResumeNotExists(Object searchKey, String uuid) {
        if (searchKey != null) {
            throw new ExistStorageException(uuid);
        }
    }

    @Override
    protected void addResume(Resume r, Object searchKey) {
        storage.add(r);
    }

    @Override
    protected void removeResume(Object searchKey) {
        int index = (int) searchKey;
        storage.remove(index);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        int index = (int) searchKey;
        return storage.get(index);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}