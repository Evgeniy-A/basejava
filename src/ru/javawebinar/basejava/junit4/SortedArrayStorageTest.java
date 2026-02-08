package ru.javawebinar.basejava.junit4;

import ru.javawebinar.basejava.storage.SortedArrayStorage;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }
}