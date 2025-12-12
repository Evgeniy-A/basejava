import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int CAPACITY = 10000;
    private Resume[] storage = new Resume[CAPACITY];
    private int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        if (size >= CAPACITY) {
            throw new IllegalArgumentException("Массив данных переполнен");
        }
        for (int i = 0; i < size; i++) {
            if (storage[i].equals(r)) {
                throw new IllegalArgumentException("Такое резюме уже есть в базе");
            }
        }
        storage[size++] = r;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        throw new IllegalArgumentException("Резюме не найдено");
    }

    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                storage[i] = storage[size - 1];
                storage[--size] = null;
                return;
            }
        }
        throw new IllegalArgumentException("Резюме не найдено");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }
}