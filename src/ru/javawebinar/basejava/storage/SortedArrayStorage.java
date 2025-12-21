package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    public void addResume(Resume r, int index) {
        index = -index - 1;
        System.arraycopy(storage, index, storage,
                index + 1, size - index);
        storage[index] = r;
        size++;
    }

    @Override
    public void removeAt(int index) {
        int countMoved = size - index - 1;
        System.arraycopy(storage, index + 1, storage,
                index, countMoved);
    }
}