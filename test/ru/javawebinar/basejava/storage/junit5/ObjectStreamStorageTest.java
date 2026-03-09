package ru.javawebinar.basejava.storage.junit5;

import ru.javawebinar.basejava.storage.ObjectStreamStorage;

public class ObjectStreamStorageTest extends AbstractStorageTest {
    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIR));
    }
}