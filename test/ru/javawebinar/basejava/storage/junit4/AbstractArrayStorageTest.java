package ru.javawebinar.basejava.storage.junit4;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume FIRST_RESUME = new Resume(UUID_1);
    private static final Resume SECOND_RESUME = new Resume(UUID_2);
    private static final Resume THRID_RESUME = new Resume(UUID_3);
    private static final Resume FOURTH_RESUME = new Resume(UUID_4);
    private static final int STORAGE_LIMIT = 9997;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUpTest() {
        storage.clear();
        storage.save(FIRST_RESUME);
        storage.save(SECOND_RESUME);
        storage.save(THRID_RESUME);
    }

    @Test()
    public void clearTest() {
        storage.clear();
        assertEquals(0, storage.getAll().length);
    }

    @Test
    public void updateTest() {
        int sizeBefore = storage.size();
        storage.update(FIRST_RESUME);
        assertSame(FIRST_RESUME, storage.get(UUID_1));
        assertEquals(sizeBefore, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistTest() {
        storage.update(FOURTH_RESUME);
    }

    @Test
    public void saveTest() {
        int sizeBefore = storage.size();
        storage.save(FOURTH_RESUME);
        assertEquals(FOURTH_RESUME, storage.get(UUID_4));
        assertEquals(sizeBefore + 1, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistTest() {
        storage.save(new Resume(UUID_1));
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
        assertSame(FIRST_RESUME, storage.get(UUID_1));
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

    @Test
    public void getAllTest() {
        Resume[] allResume = storage.getAll();
        assertEquals(storage.size(), allResume.length);
        for (Resume r : allResume) {
            Resume resume = storage.get(r.getUuid());
            assertEquals(r, resume);
        }
    }

    @Test
    public void sizeTest() {
        assertEquals(3, storage.size());
    }
}