package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.YearMonthUtil;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Serializer {
    @Override
    public void writeResume(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                SectionType type = entry.getKey();
                AbstractSection section = entry.getValue();
                dos.writeUTF(type.name());
                writeSection(type, section, dos);
            }
        }
    }

    private void writeSection(SectionType type, AbstractSection section,
                              DataOutputStream dos) throws IOException {
        switch (type) {
            case PERSONAL, OBJECTIVE -> dos.writeUTF(((TextSection) section).getContent());
            case ACHIEVEMENT, QUALIFICATIONS -> {
                List<String> contentLines = ((ListSection) section).getContentList();
                dos.writeInt(contentLines.size());
                for (String line : contentLines) {
                    dos.writeUTF(line);
                }
            }
            case EXPERIENCE, EDUCATION -> {
                List<Organization> organizations = ((OrganizationSection) section).getOrganizations();
                dos.writeInt(organizations.size());
                for (Organization org : organizations) {
                    Organization.Link link = org.getLink();
                    dos.writeUTF(link.getName());
                    dos.writeUTF(safeString(link.getUrl()));
                    List<Organization.Position> positions = org.getPositions();
                    dos.writeInt(positions.size());
                    for (Organization.Position position : positions) {
                        dos.writeUTF(position.getStartDate().toString());
                        dos.writeUTF(safeString(YearMonthUtil.toString(position.getEndDate())));
                        dos.writeUTF(position.getTitle());
                        dos.writeUTF(safeString(position.getDescription()));
                    }
                }
            }
        }
    }

    private String safeString(String value) {
        return value == null ? "" : value;
    }

    @Override
    public Resume readResume(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = Resume.of(dis.readUTF(), dis.readUTF());
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.setContacts(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType type = SectionType.valueOf(dis.readUTF());
                AbstractSection section = readSection(type, dis);
                resume.setSections(type, section);
            }
            return resume;
        }
    }

    private AbstractSection readSection(SectionType type, DataInputStream dis) throws IOException {
        return switch (type) {
            case PERSONAL, OBJECTIVE -> new TextSection(dis.readUTF());
            case ACHIEVEMENT, QUALIFICATIONS -> {
                int size = dis.readInt();
                List<String> contentLines = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    contentLines.add(dis.readUTF());
                }
                yield new ListSection(contentLines);
            }
            case EXPERIENCE, EDUCATION -> {
                int orgSize = dis.readInt();
                List<Organization> organizations = new ArrayList<>(orgSize);
                for (int i = 0; i < orgSize; i++) {
                    String name = dis.readUTF();
                    String url = restoreString(dis.readUTF());
                    Organization.Link link = new Organization.Link(name, url);
                    int posSize = dis.readInt();
                    List<Organization.Position> positions = new ArrayList<>(posSize);
                    for (int j = 0; j < posSize; j++) {
                        YearMonth startDate = YearMonthUtil.fromString(dis.readUTF());
                        YearMonth endDate = YearMonthUtil.fromString(dis.readUTF());
                        String title = dis.readUTF();
                        String description = restoreString(dis.readUTF());
                        positions.add(new Organization.Position(startDate, endDate, title, description));
                    }
                    organizations.add(new Organization(link, positions));
                }
                yield new OrganizationSection(organizations);
            }
        };
    }

    private String restoreString(String value) {
        return value.isEmpty() ? null : value;
    }
}