package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void save(Resume r) {
        if (size >= CAPACITY) {
            throw new IllegalArgumentException("Массив данных переполнен");
        }
        String savedUuid = r.getUuid();
        int saveIndex = binarySearchByUuid(savedUuid);
        if (saveIndex >= 0) {
            throw new IllegalArgumentException(
                    String.format("Резюме с uuid %s уже есть в базе", r.getUuid()));
        }
        saveIndex = -saveIndex - 1;
        System.arraycopy(storage, saveIndex, storage,
                saveIndex + 1, size - saveIndex);
        storage[saveIndex] = r;
        size++;
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