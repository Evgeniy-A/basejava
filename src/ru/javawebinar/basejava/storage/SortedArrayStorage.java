package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;
import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void clear() {
    }

    @Override
    public void update(Resume r) {
    }

    @Override
    public void save(Resume r) {
    }

    @Override
    public void delete(String uuid) {
    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    protected int findIndex(String uuid) {
        int index = binarySearchByUuid(uuid);
        if (index >= 0) {
            return index;
        }
        throw new IllegalArgumentException(
                String.format("Резюме с uuid %s не найдено", uuid));
    }

    private int binarySearchByUuid(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}