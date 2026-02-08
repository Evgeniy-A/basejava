package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected Integer findSearchKey(String uuid) {
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
    public void removeResume(Object searchKey) {
        int index = (int) searchKey;
        storage[index] = storage[size - 1];
        deleteLast();
    }
}