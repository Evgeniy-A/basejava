import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int capacity = 10000;
    private Resume[] storage = new Resume[capacity];
    private int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        if (size >= capacity) {
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
        int indexDeleteResume = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                indexDeleteResume = i;
                break;
            }
        }
        if (indexDeleteResume == -1) {
            throw new IllegalArgumentException("Резюме не найдено");
        }
        for (int i = indexDeleteResume; i < size - 1; i++) {
            storage[i] = storage[i + 1];
        }
        storage[--size] = null;
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