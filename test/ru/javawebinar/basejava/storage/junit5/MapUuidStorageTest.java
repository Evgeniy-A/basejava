package ru.javawebinar.basejava.storage.junit5;

import ru.javawebinar.basejava.storage.MapUuidStorage;

class MapUuidStorageTest extends AbstractStorageTest {
    public MapUuidStorageTest() {
        super(new MapUuidStorage());
    }
}