package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private final List<String> contentList;

    public ListSection(List<String> contentList) {
        Objects.requireNonNull(contentList, "contentList must not be null");
        this.contentList = contentList;
    }

    public void addContent(String content) {
        contentList.add(content);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(contentList, that.contentList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(contentList);
    }

    @Override
    public String toString() {
        return String.join(System.lineSeparator(), contentList);
    }
}