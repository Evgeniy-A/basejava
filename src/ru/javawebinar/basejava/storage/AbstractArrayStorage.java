package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int CAPACITY = 10000;
    protected Resume[] storage = new Resume[CAPACITY];
    protected int size = 0;

    protected abstract int findIndex(String uuid);

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public final void update(Resume r) {
        int targetIndex = findIndex(r.getUuid());
        storage[targetIndex] = r;
    }

    public abstract void save(Resume r);

    public final Resume get(String uuid) {
        int targetIndex = findIndex(uuid);
        return storage[targetIndex];
    }

    public final void delete(String uuid) {
        int indexForDel = findIndex(uuid);
        storage[indexForDel] = storage[size - 1];
        storage[--size] = null;
    }

    public final Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public final int size() {
        return size;
    }
}
