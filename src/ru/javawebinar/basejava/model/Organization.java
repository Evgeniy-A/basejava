package ru.javawebinar.basejava.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import ru.javawebinar.basejava.util.YearMonthAdapter;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;
    private Link link;
    private List<Position> positions;

    public Organization() {
    }

    public Organization(Link link, List<Position> positions) {
        Objects.requireNonNull(positions, "positions must not be null");
        this.link = link;
        this.positions = positions;
    }

    public Organization(String url, String name, Position... positions) {
        this(name, url, Arrays.asList(positions));
    }

    public Organization(String url, String name, List<Position> positions) {
        Objects.requireNonNull(positions, "positions must not be null");
        this.link = new Link(url, name);
        this.positions = positions;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(link, that.link) && Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, positions);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(link.toString()).append(System.lineSeparator());
        for (int i = 0; i < positions.size(); i++) {
            sb.append(positions.get(i));
            if (i < positions.size() - 1) {
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Link implements Serializable {
        private static final long serialVersionUID = 1L;
        private String url;
        private String name;

        public Link() {
        }

        public Link(String name, String url) {
            Objects.requireNonNull(name, "name must not be null");
            this.url = url;
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Link link = (Link) o;
            return Objects.equals(url, link.url) && Objects.equals(name, link.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(url, name);
        }

        @Override
        public String toString() {
            return (url == null ? "" : url + " ") + name;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {
        private static final long serialVersionUID = 1L;
        @XmlJavaTypeAdapter(YearMonthAdapter.class)
        private YearMonth startDate;
        @XmlJavaTypeAdapter(YearMonthAdapter.class)
        private YearMonth endDate;
        private String title;
        private String description;

        public Position() {
        }

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
            return startDate + " - " + endDate + " " + title + description;
        }
    }
}