package ru.javawebinar.basejava.util;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.YearMonth;

public class YearMonthAdapter extends XmlAdapter<String, YearMonth> {
    @Override
    public YearMonth unmarshal(String value) {
        return YearMonthUtil.fromString(value);
    }

    @Override
    public String marshal(YearMonth value) {
        return YearMonthUtil.toString(value);
    }
}