package ru.javawebinar.basejava.model;

import java.time.YearMonth;
import java.util.Objects;

public class Position {
    private final YearMonth startDate;
    private final YearMonth endDate;
    private final String title;
    private final String description;

    public Position(YearMonth startDate, YearMonth endDate, String title, String description) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    public YearMonth getStartDate() {
        return startDate;
    }

    public YearMonth getEndDate() {
        return endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(startDate, position.startDate) &&
               Objects.equals(endDate, position.endDate) &&
               Objects.equals(title, position.title) &&
               Objects.equals(description, position.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, title, description);
    }

    @Override
    public String toString() {
        String endDate = (this.endDate == null) ? "Сейчас" : this.endDate.toString();
        return startDate + " - " + endDate + " " + title;
    }
}