package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected Integer doFindSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void insertResume(Resume r, int index) {
        storage[size] = r;
    }

    @Override
    public void doRemove(Object index) {
        storage[(int) index] = storage[size - 1];
        deleteLast();
    }
}