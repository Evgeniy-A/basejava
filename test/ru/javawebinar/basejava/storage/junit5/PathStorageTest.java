package ru.javawebinar.basejava.storage.junit5;

import ru.javawebinar.basejava.storage.serializer.ObjectStreamSerializer;
import ru.javawebinar.basejava.storage.PathStorage;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}