package ru.javawebinar.basejava.junit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final int STORAGE_LIMIT = 9997;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
        storage.get(UUID_1);
    }

    @Test
    public void update() {
        int sizeBefore = storage.size();
        Resume r = new Resume(UUID_1);
        storage.update(r);
        Assert.assertEquals(r, storage.get(UUID_1));
        Assert.assertEquals(storage.size(), sizeBefore);
    }

    @Test
    public void save() {
        int sizeBefore = storage.size();
        Resume r = new Resume("uuid4");
        storage.save(r);
        Assert.assertEquals(r, storage.get("uuid4"));
        Assert.assertEquals(storage.size(), sizeBefore + 1);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID_1));
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        for (int i = 0; i < STORAGE_LIMIT; i++) {
            try {
                storage.save(new Resume());
            } catch (StorageException e) {
                Assert.fail("Переполнение произошло раньше времени");
            }
        }
        storage.save(new Resume());
    }

    @Test
    public void get() {
        Assert.assertEquals(new Resume(UUID_1), storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void delete() {
        int sizeBefore = storage.size();
        storage.delete(UUID_1);
        Assert.assertEquals(storage.size(), sizeBefore - 1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void getAll() {
        Resume[] allResume = storage.getAll();
        Assert.assertEquals(storage.size(), allResume.length);
        for (Resume r : allResume) {
            Resume resume = storage.get(r.getUuid());
            Assert.assertEquals(r, resume);
        }
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }
}