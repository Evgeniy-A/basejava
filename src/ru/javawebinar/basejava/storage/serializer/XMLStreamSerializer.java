package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.XMLParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XMLStreamSerializer implements Serializer {
    private XMLParser xmlParser;

    public XMLStreamSerializer() {
        xmlParser = new XMLParser(Resume.class, Organization.class,
                Organization.Link.class, Organization.Position.class,
                OrganizationSection.class, TextSection.class);
    }

    @Override
    public void writeResume(Resume r, OutputStream oss) throws IOException {
        try (Writer w = new OutputStreamWriter(oss, StandardCharsets.UTF_8)) {
            xmlParser.marshall(r, w);
        }
    }

    @Override
    public Resume readResume(InputStream is) throws IOException {
        try (Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(r);
        }
    }
}