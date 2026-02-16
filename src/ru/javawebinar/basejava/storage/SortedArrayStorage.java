package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
    private static final Comparator<Resume> RESUME_COMPARATOR = new Comparator<>() {
        @Override
        public int compare(Resume o1, Resume o2) {
            return o1.getUuid().compareTo(o2.getUuid());
        }
    };

    @Override
    protected Integer findSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }

    @Override
    public void insertResume(Resume r, int index) {
        index = -index - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = r;
    }

    @Override
    public void deleteResume(int index) {
        int countMoved = size - index - 1;
        System.arraycopy(storage, index + 1, storage, index, countMoved);
    }
}