package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.serializer.Serializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final Serializer serializer;

    public PathStorage(String dir, Serializer serializer) {
        directory = Paths.get(dir);
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory");
        }
        Objects.requireNonNull(serializer, "serializer must not be null");
        this.serializer = serializer;
    }

    @Override
    protected Path findSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void replaceResume(Resume r, Path path) {
        try {
            serializer.writeResume(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("IO error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void addResume(Resume r, Path path) {
        try {
            serializer.writeResume(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("IO error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void removeResume(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Delete Resume error", directory.toAbsolutePath().toString(), e);
        }
    }

    @Override
    protected Resume getResume(Path path) {
        try {
            return serializer.readResume(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", directory.toAbsolutePath().toString(), e);
        }
    }

    @Override
    protected List<Resume> getResumesAsList() {
        List<Resume> list = new ArrayList<>();
        for (Path path : getAllFiles()) {
            list.add(getResume(path));
        }
        return list;
    }

    @Override
    public void clear() {
        getAllFiles().forEach(this::removeResume);
    }

    @Override
    public int size() {
        return getAllFiles().size();
    }

    private List<Path> getAllFiles() {
        try (Stream<Path> stream = Files.list(directory)) {
            return stream.toList();
        } catch (IOException e) {
            throw new StorageException("Path error", e);
        }
    }
}