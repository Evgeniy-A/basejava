package ru.javawebinar.basejava.storage.junit5;

import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.storage.FileStorage;
import ru.javawebinar.basejava.storage.ObjectStreamSerializer;

import static org.junit.jupiter.api.Assertions.*;

class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}