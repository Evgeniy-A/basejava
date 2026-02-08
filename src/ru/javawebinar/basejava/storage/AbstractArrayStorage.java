package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int CAPACITY = 10000;
    protected Resume[] storage = new Resume[CAPACITY];
    protected int size = 0;

    protected abstract void insertResume(Resume r, int index);

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    protected void deleteLast() {
        storage[--size] = null;
    }

    @Override
    protected void replaceResume(Resume r, Object searchKey) {
        int index = (int) searchKey;
        storage[index] = r;
    }

    @Override
    protected void checkResumeExists(Object searchKey, String uuid) {
        int index = (int) searchKey;
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    protected void checkResumeNotExists(Object searchKey, String uuid) {
        int index = (int) searchKey;
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        }
    }

    @Override
    protected final void addResume(Resume r, Object searchKey) {
        checkCapacity(r.getUuid());
        int index = (int) searchKey;
        insertResume(r, index);
        size++;
    }

    private void checkCapacity(String uuid) {
        if (size >= CAPACITY) {
            throw new StorageException("Хранилище переполнено.", uuid);
        }
    }

    @Override
    protected Resume doGet(Object searchKey) {
        int index = (int) searchKey;
        return storage[index];
    }
}