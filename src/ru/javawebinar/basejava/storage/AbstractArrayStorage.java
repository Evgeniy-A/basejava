package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int CAPACITY = 10000;
    protected Resume[] storage = new Resume[CAPACITY];
    protected int size = 0;

    protected abstract int findIndex(String uuid);

    public Resume get(String uuid) {
        int targetIndex = findIndex(uuid);
        return storage[targetIndex];
    }

    public int size() {
        return size;
    }
}
