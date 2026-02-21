package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Resume findSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
    }

    @Override
    protected void replaceResume(Resume r, Resume searchKey) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void addResume(Resume r, Resume searchKey) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void removeResume(Resume searchKey) {
        storage.remove((searchKey).getUuid());
    }

    @Override
    protected Resume getResume(Resume searchKey) {
        return searchKey;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> getResumesAsList() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }
}