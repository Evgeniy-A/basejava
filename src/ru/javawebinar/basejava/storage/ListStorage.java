package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected Integer findSearchKey(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Object index) {
        return index != null;
    }

    @Override
    protected void replaceResume(Resume r, Object index) {
        storage.set((int) index, r);
    }

    @Override
    protected void addResume(Resume r, Object index) {
        storage.add(r);
    }

    @Override
    protected void removeResume(Object index) {
        storage.remove((int) index);
    }

    @Override
    protected Resume getResume(Object index) {
        return storage.get((int) index);
    }

    @Override
    protected List<Resume> getResumesAsList() {
        return storage;
    }

    @Override
    public int size() {
        return storage.size();
    }
}