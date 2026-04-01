package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.YearMonthUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Serializer {
    @Override
    public void writeResume(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            writeCollection(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            Map<SectionType, AbstractSection> sections = r.getSections();
            writeCollection(dos, sections.entrySet(), entry -> {
                SectionType type = entry.getKey();
                AbstractSection section = entry.getValue();
                dos.writeUTF(type.name());
                writeSection(type, section, dos);
            });
        }
    }

    private void writeSection(SectionType type, AbstractSection section,
                              DataOutputStream dos) throws IOException {
        switch (type) {
            case PERSONAL, OBJECTIVE -> dos.writeUTF(((TextSection) section).getContent());
            case ACHIEVEMENT, QUALIFICATIONS ->
                    writeCollection(dos, ((ListSection) section).getContentList(), dos::writeUTF);
            case EXPERIENCE, EDUCATION -> {
                writeCollection(dos, ((OrganizationSection) section).getOrganizations(), org -> {
                    dos.writeUTF(org.getLink().getName());
                    dos.writeUTF(safeString(org.getLink().getUrl()));
                    writeCollection(dos, org.getPositions(), position -> {
                        dos.writeUTF(position.getStartDate().toString());
                        dos.writeUTF(safeString(YearMonthUtil.toString(position.getEndDate())));
                        dos.writeUTF(position.getTitle());
                        dos.writeUTF(safeString(position.getDescription()));
                    });
                });
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
            readItems(dis, () -> resume.setContacts(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readItems(dis, () -> {
                SectionType type = SectionType.valueOf(dis.readUTF());
                AbstractSection section = readSection(type, dis);
                resume.setSections(type, section);
            });
            return resume;
        }
    }

    private AbstractSection readSection(SectionType type, DataInputStream dis) throws IOException {
        return switch (type) {
            case PERSONAL, OBJECTIVE -> new TextSection(dis.readUTF());
            case ACHIEVEMENT, QUALIFICATIONS -> new ListSection(readList(dis, dis::readUTF));
            case EXPERIENCE, EDUCATION -> new OrganizationSection(
                    readList(dis, () -> new Organization(
                            new Organization.Link(dis.readUTF(), restoreString(dis.readUTF())),
                            readList(dis, () -> new Organization.Position(
                                    YearMonthUtil.fromString(dis.readUTF()),
                                    YearMonthUtil.fromString(dis.readUTF()),
                                    dis.readUTF(),
                                    restoreString(dis.readUTF())
                            ))
                    ))
            );
        };
    }

    private String restoreString(String value) {
        return value.isEmpty() ? null : value;
    }

    private interface ElementWriter<T> {
        void write(T t) throws IOException;
    }

    private interface ElementReader<T> {
        T read() throws IOException;
    }

    private interface ElementProcessor {
        void process() throws IOException;
    }

    private <T> void writeCollection(
            DataOutputStream dos, Collection<T> collection, ElementWriter<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            writer.write(item);
        }
    }

    private void readItems(DataInputStream dis, ElementProcessor processor) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            processor.process();
        }
    }

    private <T> List<T> readList(DataInputStream dis, ElementReader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> contentLines = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            contentLines.add(reader.read());
        }
        return contentLines;
    }
}