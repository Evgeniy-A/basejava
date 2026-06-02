package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.util.UUID;

public class TestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();
    public static final Resume RESUME_1 = ResumeTestData.creatResume(UUID_1, "Джек Торранс");
    public static final Resume RESUME_2 = ResumeTestData.creatResume(UUID_2, "Джон Коффи");
    public static final Resume RESUME_3 = ResumeTestData.creatResume(UUID_3, "Кэрри Уайт");
    public static final Resume RESUME_4 = ResumeTestData.creatResume(UUID_4, "Генри Дэнверс");
}
