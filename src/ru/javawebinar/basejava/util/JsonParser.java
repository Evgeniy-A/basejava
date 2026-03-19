package ru.javawebinar.basejava.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.javawebinar.basejava.model.AbstractSection;

import java.io.Reader;
import java.io.Writer;
import java.time.YearMonth;

public class JsonParser {
    private static Gson GSON = new GsonBuilder()
            .registerTypeAdapter(YearMonth.class, new GsonYearMonthAdapter())
            .registerTypeAdapter(AbstractSection.class, new JsonSectionAdapter<>())
            .create();

    public static <T> T read(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    public static <T> void write(T objrct, Writer write) {
        GSON.toJson(objrct, write);
    }
}