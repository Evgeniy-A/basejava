package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {
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

    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        throw new IllegalArgumentException(
                String.format("Резюме с uuid %s не найдено", uuid));
    }
}