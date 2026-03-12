package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ObjectStreamPathStorage extends AbstractPathStorage {
    public ObjectStreamPathStorage(String dir) {
        super(dir);
    }

    protected void writeResume(Resume r, Path path) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
            oos.writeObject(r);
        }
    }

    protected Resume readResume(Path path) throws IOException {
        try (ObjectInputStream iis = new ObjectInputStream(Files.newInputStream(path))) {
            return (Resume) iis.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException(null, "Error read resume", e);
        }
    }
}