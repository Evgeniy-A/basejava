package ru.javawebinar.basejava.storage.junit5;

import ru.javawebinar.basejava.storage.ObjectStreamPathStorage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new ObjectStreamPathStorage(STORAGE_DIR));
    }
}