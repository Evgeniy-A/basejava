package ru.javawebinar.basejava.storage.junit5;

import ru.javawebinar.basejava.storage.FileStorage;
import ru.javawebinar.basejava.storage.serializer.ObjectStreamSerializer;

class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}