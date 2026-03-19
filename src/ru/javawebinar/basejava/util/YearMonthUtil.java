package ru.javawebinar.basejava.util;

import java.time.YearMonth;

public class YearMonthUtil {
    public static String toString(YearMonth value) {
        return value != null ? value.toString() : null;
    }

    public static YearMonth fromString(String value) {
        return value != null ? YearMonth.parse(value) : null;
    }
}