package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void addResume(Resume r, int index) {
        index = size;
        storage[index] = r;
        size++;
    }

    @Override
    public void removeAt(int index) {
        storage[index] = storage[size - 1];
    }
}