package ru.javawebinar.basejava.storage.junit5;

import ru.javawebinar.basejava.storage.ListStorage;

class ListStorageTest extends AbstractStorageTest {
    public ListStorageTest() {
        super(new ListStorage());
    }
}