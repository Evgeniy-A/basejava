package ru.topjava.webapp.storage;

import ru.topjava.webapp.model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int CAPACITY = 10000;
    private Resume[] storage = new Resume[CAPACITY];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int targetIndex = findIndex(r.getUuid());
        storage[targetIndex] = r;
    }

    public void save(Resume r) {
        if (size >= CAPACITY) {
            throw new IllegalArgumentException("Массив данных переполнен");
        }
        for (int i = 0; i < size; i++) {
            if (storage[i].equals(r)) {
                throw new IllegalArgumentException(
                        String.format("Резюме с uuid %s уже есть в базе", r.getUuid()));
            }
        }
        storage[size++] = r;
    }

    public Resume get(String uuid) {
        int targetIndex = findIndex(uuid);
        return storage[targetIndex];
    }

    public void delete(String uuid) {
        int indexForDel = findIndex(uuid);
        storage[indexForDel] = storage[size - 1];
        storage[--size] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        throw new IllegalArgumentException(
                String.format("Резюме с uuid %s не найдено", uuid));
    }
}