package ru.javawebinar.basejava.storage.junit5;

import ru.javawebinar.basejava.storage.PathStorage;
import ru.javawebinar.basejava.storage.serializer.XMLStreamSerializer;

public class XMLPathStorageTest extends AbstractStorageTest {
    public XMLPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new XMLStreamSerializer()));
    }
}