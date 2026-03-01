package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.basejava.model.ContactType.*;
import static ru.javawebinar.basejava.model.SectionType.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume testResume = creatResume("12345", "Григорий Кислин");
        printResume(testResume);
    }

    private static void printResume(Resume testResume) {
        System.out.printf("%5s--- %s ---\n", "", testResume.getFullName());
        for (ContactType type : ContactType.values()) {
            String contact = testResume.getContact(type);
            if (contact != null) {
                System.out.println(type.getTitle() + ": " + contact);
            }
        }
        for (SectionType type : SectionType.values()) {
            AbstractSection section = testResume.getSection(type);
            if (section != null) {
                System.out.println("\n" + type.getTitle() + ":\n" + section);
            }
        }
    }

    public static Resume creatResume(String uuid, String fullName) {
        Resume testResume = Resume.of(uuid, fullName);
        creatContactsResume(testResume);
        creatSectionsResume(testResume);
        return testResume;
    }

    private static void creatContactsResume(Resume testResume) {
        testResume.setContacts(PHONE, "+7(921) 855-0482");
        testResume.setContacts(SKYPE, "skype:grigory.kislin");
        testResume.setContacts(MAIL, "gkislin@yandex.ru");
        testResume.setContacts(LINKEDIN, "https://www.linkedin.com/in/gkislin");
        testResume.setContacts(GITHUB, "https://github.com/gkislin");
        testResume.setContacts(STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");
        testResume.setContacts(HOME_PAGE, "http://gkislin.ru/");
    }

    private static void creatSectionsResume(Resume testResume) {
        testResume.setSections(OBJECTIVE, new TextSection(
                "Ведущий стажировок и корпоративного " +
                "обучения по Java Web и Enterprise технологиям"));
        testResume.setSections(PERSONAL, new TextSection(
                "Аналитический склад ума, сильная логика, " +
                "креативность, инициативность. Пурист кода и архитектуры."));
        List<String> contentList = new ArrayList<>();
        contentList.add("Организация команды и успешная реализация Java " +
                        "проектов для сторонних заказчиков: приложения " +
                        "автопарк на стеке Spring Cloud/микросервисы, " +
                        "система мониторинга показателей спортсменов на " +
                        "Spring Boot, участие в проекте МЭШ на Play-2, " +
                        "многомодульный Spring Boot + Vaadin проект " +
                        "для комплексных DIY смет");
        contentList.add("С 2013 года: разработка проектов \"Разработка Web приложения\"," +
                        "\"Java Enterprise\", \"Многомодульный maven. Многопоточность. " +
                        "XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное " +
                        "взаимодействие (JMS/AKKA)\". Организация онлайн стажировок " +
                        "и ведение проектов. Более 3500 выпускников.");
        testResume.setSections(ACHIEVEMENT, new ListSection(contentList));
        List<String> contentSecondList = new ArrayList<>();
        contentSecondList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        contentSecondList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        testResume.setSections(QUALIFICATIONS, new ListSection(contentSecondList));
        testResume.setSections(EXPERIENCE, new OrganizationSection(
                new Organization("Java Online Projects", "http://javaops.ru/",
                        new Position(
                                YearMonth.of(2013, 10),
                                null,
                                "Автор проекта",
                                "Создание, организация и проведение Java онлайн проектов."
                        )
                ),
                new Organization("Wrike", "https://www.wrike.com/",
                        new Position(
                                YearMonth.of(2014, 10),
                                YearMonth.of(2016, 1),
                                "Старший разработчик",
                                "Проектирование и разработка онлайн платформы."
                        )
                )
        ));
        testResume.setSections(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Coursera", "https://www.coursera.org/",
                                new Position(
                                        YearMonth.of(2013, 3),
                                        YearMonth.of(2013, 5),
                                        "Functional Programming in Scala",
                                        null
                                )
                        ),
                        new Organization("Luxoft", "https://www.luxoft-training.ru" +
                                                   "/training/catalog/course.html?ID=22366",
                                new Position(
                                        YearMonth.of(2011, 3),
                                        YearMonth.of(2011, 4),
                                        "\n" +
                                        "Курс 'Объектно-ориентированный анализ ИС. " +
                                        "Концептуальное моделирование на UML.'",
                                        null
                                )
                        )
                )
        );
    }
}