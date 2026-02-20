package ru.javawebinar.basejava.storage.junit5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {
    protected final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_1 = Resume.ofUuid(UUID_1);
    private static final Resume RESUME_2 = Resume.ofUuid(UUID_2);
    private static final Resume RESUME_3 = Resume.ofUuid(UUID_3);
    private static final Resume RESUME_4 = Resume.ofUuid(UUID_4);

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    void clearTest() {
        storage.clear();
        assertStorageSizeAndContents(0, List.of());
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
        int expectedSize = storage.size() + 1;
        storage.save(RESUME_4);
        assertStorageSizeAndContents(expectedSize, List.of(RESUME_1, RESUME_2, RESUME_3, RESUME_4));
    }

    @Test
    void saveExistTest() {
        assertThrows(ExistStorageException.class, () ->
                storage.save(RESUME_1));
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
        int expectedSize = storage.size() - 1;
        storage.delete(UUID_1);
        assertStorageSizeAndContents(expectedSize, List.of(RESUME_2, RESUME_3));
    }

    @Test
    void deleteNotExistTest() {
        assertThrows(NotExistStorageException.class, () ->
                storage.delete(UUID_4));
    }

    @Test
    void getAllSortedTest() {
        assertStorageSizeAndContents(storage.size(), List.of(RESUME_1, RESUME_2, RESUME_3));
    }

    @Test
    void sizeTest() {
        assertEquals(3, storage.size());
    }

    private void assertStorageSizeAndContents(int expectedSize, List<Resume> expected) {
        assertEquals(expected, storage.getAllSorted());
        assertEquals(expectedSize, storage.size());
    }
}