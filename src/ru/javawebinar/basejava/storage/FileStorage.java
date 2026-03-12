package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;

    public FileStorage(String dir) {
        Objects.requireNonNull(dir, "directory must not be null");
        directory = new File(dir);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable");
        }
    }

    private Resume readResume(InputStream is) throws IOException {
        try (ObjectInputStream iis = new ObjectInputStream(is)) {
            return (Resume) iis.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException(null, "Error read resume", e);
        }
    }

    private void writeResume(Resume r, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(r);
        }
    }

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
            writeResume(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void addResume(Resume r, File file) {
        try {
            file.createNewFile();
            writeResume(r, new BufferedOutputStream(new FileOutputStream(file)));
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
            return readResume(new BufferedInputStream(new FileInputStream(file)));
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