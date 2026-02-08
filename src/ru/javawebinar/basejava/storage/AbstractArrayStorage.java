package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int CAPACITY = 10000;
    protected Resume[] storage = new Resume[CAPACITY];
    protected int size = 0;

    protected abstract int findIndex(String uuid);

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int targetIndex = findIndex(r.getUuid());
        storage[targetIndex] = r;
    }

    public abstract void save(Resume r);

    public Resume get(String uuid) {
        int targetIndex = findIndex(uuid);
        return storage[targetIndex];
    }

    public void delete(String uuid) {
        int indexForDel = findIndex(uuid);
        storage[indexForDel] = storage[size - 1];
        storage[--size] = null;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
