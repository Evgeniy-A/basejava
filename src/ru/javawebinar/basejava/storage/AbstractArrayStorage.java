package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int CAPACITY = 10000;
    protected Resume[] storage = new Resume[CAPACITY];
    protected int size = 0;

    protected abstract int findIndex(String uuid);

    public abstract void addResume(Resume r, int index);

    public abstract void removeResume(int index);

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public final void update(Resume r) {
        String uuid = r.getUuid();
        int index = findIndex(uuid);
        checkResumeExists(uuid, index);
        storage[index] = r;
    }

    public final void save(Resume r) {
        String uuid = r.getUuid();
        checkCapacity(uuid);
        int index = findIndex(uuid);
        checkResumeNotExists(uuid, index);
        addResume(r, index);
        size++;
    }

    private void checkCapacity(String uuid) {
        if (size >= CAPACITY) {
            throw new StorageException(uuid);
        }
    }

    private void checkResumeNotExists(String uuid, int index) {
        if (index >= 0) {
            throw new ExistStorageException("Хранилище переполнено.");
        }
    }

    public final Resume get(String uuid) {
        int index = findIndex(uuid);
        checkResumeExists(uuid, index);
        return storage[index];
    }

    public final void delete(String uuid) {
        int index = findIndex(uuid);
        checkResumeExists(uuid, index);
        removeResume(index);
        storage[--size] = null;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private void checkResumeExists(String uuid, int index) {
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
    }
}