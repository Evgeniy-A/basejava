package ru.javawebinar.basejava.storage.junit5;

import ru.javawebinar.basejava.storage.SortedArrayStorage;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }
}