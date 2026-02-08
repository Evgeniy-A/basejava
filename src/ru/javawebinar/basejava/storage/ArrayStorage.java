package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class ArrayStorage extends AbstractArrayStorage {
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index == -1) {
            System.out.println("Resume " + r.getUuid() + " not exist");
        } else {
            storage[index] = r;
        }
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

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " not exist");
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
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