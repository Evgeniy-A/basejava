package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;

public class ObjectStreamStorage extends AbstractFileStorage {
    public ObjectStreamStorage(File directory) {
        super(directory);
    }

    @Override
    protected void writeResume(Resume r, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(r);
        }
    }

    @Override
    protected Resume readResume(InputStream is) throws IOException {
        try (ObjectInputStream iis = new ObjectInputStream(is)) {
            return (Resume) iis.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException(null, "Error read resume", e);
        }
    }
}