package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable");
        }
        this.directory = directory;
    }

    protected abstract void writeResume(Resume r, File file) throws IOException;

    protected abstract Resume readResume(File file) throws IOException;

    @Override
    protected File findSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void replaceResume(Resume r, File file) {
        try {
            writeResume(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void addResume(Resume r, File file) {
        try {
            file.createNewFile();
            writeResume(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void removeResume(File file) {
        if (!file.delete()) {
            throw new StorageException("Delete Resume error", file.getName());
        }
    }

    @Override
    protected Resume getResume(File file) {
        try {
            return readResume(file);
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> getResumesAsList() {
        List<Resume> list = new ArrayList<>();
        for (File file : getAllFiles()) {
            list.add(getResume(file));
        }
        return list;
    }

    @Override
    public void clear() {
        for (File file : getAllFiles()) {
            removeResume(file);
        }
    }

    @Override
    public int size() {
        return getAllFiles().length;
    }

    private File[] getAllFiles() {
        File[] allFiles = directory.listFiles(File::isFile);
        if (allFiles == null) {
            throw new StorageException("Directory error", directory.getAbsolutePath());
        }
        return allFiles;
    }
}