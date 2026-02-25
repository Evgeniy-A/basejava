package ru.javawebinar.basejava.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final String url;
    private final String name;
    private final List<Position> positions;

    public Organization(String name, String url, Position... positions) {
        this(name, url, Arrays.asList(positions));
    }

    public Organization(String url, String name, List<Position> positions) {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(positions, "positions must not be null");
        this.url = url;
        this.name = name;
        this.positions = positions;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(url, that.url) && Objects.equals(name, that.name) &&
               Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, name, positions);
    }

    @Override
    public String toString() {
        return url + "," + name + "," + positions;
    }
}