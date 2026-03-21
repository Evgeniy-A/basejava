package ru.javawebinar.basejava.model;

import jakarta.xml.bind.annotation.XmlSeeAlso;
import java.io.Serializable;

@XmlSeeAlso({TextSection.class, ListSection.class, OrganizationSection.class})
public abstract class AbstractSection implements Serializable {
}