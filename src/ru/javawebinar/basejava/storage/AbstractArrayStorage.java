package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int CAPACITY = 10000;
    protected Resume[] storage = new Resume[CAPACITY];
    protected int size = 0;

    protected abstract Integer findSearchKey(String uuid);

    protected abstract void insertResume(Resume r, int index);

    protected abstract void deleteResume(int index);

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected boolean isExist(Object index) {
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
        deleteResume((int) index);
        storage[--size] = null;
    }

    @Override
    protected Resume getResume(Object index) {
        return storage[(int) index];
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }
}