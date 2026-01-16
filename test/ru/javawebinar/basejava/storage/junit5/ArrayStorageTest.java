package ru.javawebinar.basejava.storage.junit5;

import ru.javawebinar.basejava.storage.ArrayStorage;
import ru.javawebinar.basejava.storage.junit4.AbstractArrayStorageTest;

public class ArrayStorageTest extends AbstractArrayStorageTest {
    public ArrayStorageTest() {
        super(new ArrayStorage());
    }
}