package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Serializer {
    void writeResume(Resume r, OutputStream os) throws IOException;

    Resume readResume(InputStream is) throws IOException;
}