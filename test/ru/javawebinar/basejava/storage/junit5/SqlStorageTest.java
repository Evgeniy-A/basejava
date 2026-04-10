package ru.javawebinar.basejava.storage.junit5;

import ru.javawebinar.basejava.Config;

public class SqlStorageTest extends AbstractStorageTest{
    public SqlStorageTest() {
        super(Config.get().getStorage());
    }
}
