package ru.javawebinar.basejava.storage.junit5;

import ru.javawebinar.basejava.storage.ListStorage;

class ListStorageTest extends AbstractArrayStorageTest {
    public ListStorageTest() {
        super(new ListStorage());
    }
}