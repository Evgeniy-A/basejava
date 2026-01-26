package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int CAPACITY = 10000;
    protected Resume[] storage = new Resume[CAPACITY];
    protected int size = 0;

    protected abstract Integer doFindSearchKey(String uuid);

    protected abstract void insertResume(Resume r, int index);

    protected abstract void doRemove(Object index);

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected final Integer findSearchKey(String uuid) {
        return doFindSearchKey(uuid);
    }

    @Override
    protected boolean hasKey(Object index) {
        return (int) index >= 0;
    }

    @Override
    protected void replaceResume(Resume r, Object index) {
        storage[(int) index] = r;
    }

    @Override
    protected final void addResume(Resume r, Object index) {
        checkCapacity(r.getUuid());
        insertResume(r, (int) index);
        size++;
    }

    private void checkCapacity(String uuid) {
        if (size >= CAPACITY) {
            throw new StorageException("Хранилище переполнено.", uuid);
        }
    }

    @Override
    protected final void removeResume(Object index) {
        doRemove(index);
    }

    @Override
    protected Resume doGet(Object index) {
        return storage[(int) index];
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
}