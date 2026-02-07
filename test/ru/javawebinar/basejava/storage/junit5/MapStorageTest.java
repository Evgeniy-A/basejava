package ru.javawebinar.basejava.storage.junit5;

import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.MapStorage;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static java.util.Arrays.sort;

class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    @Test
    void getAllTest() {
        Resume[] expected = {new Resume("uuid1"), new Resume("uuid2"), new Resume("uuid3")};
        Resume[] actual = storage.getAll();
        sort(actual, Comparator.comparing(Resume::getUuid));
        assertArrayEquals(expected, actual);
    }
}