package ru.javawebinar.basejava.util;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.model.AbstractSection;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.TextSection;

import static ru.javawebinar.basejava.TestData.RESUME_1;

class JsonParserTest {

    @Test
    public void testResume() {
        String json = JsonParser.write(RESUME_1);
        System.out.println(json);
        Resume resume2 = JsonParser.read(json, Resume.class);
        Assert.assertEquals(RESUME_1, resume2);
    }

    @Test
    void write() {
        AbstractSection section1 = new TextSection("Test");
        String json = JsonParser.write(section1, AbstractSection.class);
        System.out.println(json);
        AbstractSection section2 = JsonParser.read(json, AbstractSection.class);
        Assert.assertEquals(section1, section2);
    }
}