package ru.javawebinar.basejava.storage.junit5;

import ru.javawebinar.basejava.storage.MapStorage;

class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }
}