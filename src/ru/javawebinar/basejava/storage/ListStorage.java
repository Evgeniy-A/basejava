package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
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
    protected boolean isExist(Integer index) {
        return index != null;
    }

    @Override
    protected void replaceResume(Resume r, Integer index) {
        storage.set(index, r);
    }

    @Override
    protected void addResume(Resume r, Integer index) {
        storage.add(r);
    }

    @Override
    protected void removeResume(Integer index) {
        storage.remove(index.intValue());
    }

    @Override
    protected Resume getResume(Integer index) {
        return storage.get(index);
    }

    @Override
    protected List<Resume> getResumesAsList() {
        return new ArrayList<>(storage);
    }

    @Override
    public int size() {
        return storage.size();
    }
}