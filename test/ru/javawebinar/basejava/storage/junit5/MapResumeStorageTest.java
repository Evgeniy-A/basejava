package ru.javawebinar.basejava.storage.junit5;

import ru.javawebinar.basejava.storage.MapResumeStorage;

class MapResumeStorageTest extends AbstractStorageTest {
    public MapResumeStorageTest() {
        super(new MapResumeStorage());
    }
}