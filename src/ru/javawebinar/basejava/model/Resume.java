package ru.javawebinar.basejava.model;

import java.util.Objects;
import java.util.UUID;

import static java.util.Comparator.comparing;
import static java.util.Comparator.nullsFirst;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {
    private String fullName;
    private final String uuid;

    public Resume() {
        this.uuid = UUID.randomUUID().toString();
    }

    private Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public static Resume ofUuid(String uuid) {
        return new Resume(uuid, null);
    }

    public static Resume ofFullName(String fullName) {
        return new Resume(UUID.randomUUID().toString(), fullName);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid);
    }

    @Override
    public int compareTo(Resume o) {
        return comparing(Resume::getFullName, nullsFirst(String::compareTo))
                .thenComparing(Resume::getUuid)
                .compare(this, o);
    }
}