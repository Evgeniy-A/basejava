package ru.javawebinar.basejava.storage.junit5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final Resume RESUME_4 = new Resume(UUID_4);
    private static final int STORAGE_LIMIT = 9997;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUpTest() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    void clearTest() {
        storage.clear();
        assertEquals(0, storage.getAll().length);
        assertEquals(0, storage.size());
    }

    @Test
    void updateTest() {
        int sizeBefore = storage.size();
        storage.update(RESUME_1);
        assertSame(RESUME_1, storage.get(UUID_1));
        assertEquals(sizeBefore, storage.size());
    }

    @Test
    void updateNotExistTest() {
        assertThrows(NotExistStorageException.class, () ->
                storage.update(RESUME_4));
    }

    @Test
    void saveTest() {
        int sizeBefore = storage.size();
        storage.save(RESUME_4);
        assertEquals(RESUME_4, storage.get(UUID_4));
        assertEquals(sizeBefore + 1, storage.size());
    }

    @Test
    void saveExistTest() {
        assertThrows(ExistStorageException.class, () ->
                storage.save(new Resume(UUID_1)));
    }

    @Test
    void saveOverflowTest() {
        for (int i = 0; i < STORAGE_LIMIT; i++) {
            try {
                storage.save(new Resume());
            } catch (StorageException e) {
                fail("Переполнение произошло раньше времени");
            }
        }
        assertThrows(StorageException.class, () ->
        storage.save(new Resume()));
    }

    @Test
    void getTest() {
        assertSame(RESUME_1, storage.get(UUID_1));
    }

    @Test
    void getNotExistTest() {
        assertThrows(NotExistStorageException.class, () ->
        storage.get(UUID_4));
    }

    @Test
    void deleteTest() {
        int sizeBefore = storage.size();
        storage.delete(UUID_1);
        assertEquals(sizeBefore - 1, storage.size());
        assertThrows(NotExistStorageException.class, () ->
        storage.get(UUID_1));
    }

    @Test
    void deleteNotExistTest() {
        assertThrows(NotExistStorageException.class, () ->
        storage.delete(UUID_4));
    }

    @Test
    void getAllTest() {
        Resume[] allResume = storage.getAll();
        assertEquals(storage.size(), allResume.length);
        for (Resume r : allResume) {
            Resume resume = storage.get(r.getUuid());
            assertEquals(r, resume);
        }
    }

    @Test
    void sizeTest() {
        assertEquals(3, storage.size());
    }
}