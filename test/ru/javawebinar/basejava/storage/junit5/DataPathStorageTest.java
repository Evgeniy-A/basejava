package ru.javawebinar.basejava.storage.junit5;

import ru.javawebinar.basejava.storage.PathStorage;
import ru.javawebinar.basejava.storage.serializer.DataStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest {
    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new DataStreamSerializer()));
    }
}