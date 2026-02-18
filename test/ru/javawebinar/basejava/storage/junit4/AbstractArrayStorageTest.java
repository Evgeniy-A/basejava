package ru.javawebinar.basejava.storage.junit4;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_1 = Resume.ofUuid(UUID_1);
    private static final Resume RESUME_2 = Resume.ofUuid(UUID_2);
    private static final Resume RESUME_3 = Resume.ofUuid(UUID_3);
    private static final Resume RESUME_4 = Resume.ofUuid(UUID_4);
    private static final int STORAGE_LIMIT = 9997;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test()
    public void clearTest() {
        storage.clear();
        assertEquals(0, storage.getAllSorted().size());
        assertEquals(0, storage.size());
    }

    @Test
    public void updateTest() {
        int sizeBefore = storage.size();
        storage.update(RESUME_1);
        assertSame(RESUME_1, storage.get(UUID_1));
        assertEquals(sizeBefore, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistTest() {
        storage.update(RESUME_4);
    }

    @Test
    public void saveTest() {
        int sizeBefore = storage.size();
        storage.save(RESUME_4);
        assertEquals(RESUME_4, storage.get(UUID_4));
        assertEquals(sizeBefore + 1, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistTest() {
        storage.save(RESUME_1);
    }

    @Test(expected = StorageException.class)
    public void saveOverflowTest() {
        for (int i = 0; i < STORAGE_LIMIT; i++) {
            try {
                storage.save(new Resume());
            } catch (StorageException e) {
                fail("Переполнение произошло раньше времени");
            }
        }
        storage.save(new Resume());
    }

    @Test
    public void getTest() {
        assertSame(RESUME_1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistTest() {
        storage.get(UUID_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteTest() {
        int sizeBefore = storage.size();
        storage.delete(UUID_1);
        assertEquals(sizeBefore - 1, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistTest() {
        storage.delete(UUID_4);
    }

    @org.junit.jupiter.api.Test
    void getAllSortedTest() {
        List<Resume> expected = new ArrayList<>(Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
        List<Resume> actual = storage.getAllSorted();
        assertEquals(expected, actual);
    }

    @Test
    public void sizeTest() {
        assertEquals(3, storage.size());
    }
}