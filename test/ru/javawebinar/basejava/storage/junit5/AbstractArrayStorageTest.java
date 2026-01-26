package ru.javawebinar.basejava.storage.junit5;

import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    private static final int STORAGE_LIMIT = 9997;

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
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
}