package ru.javawebinar.basejava.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.YearMonth;

public class GsonYearMonthAdapter extends TypeAdapter<YearMonth> {
    @Override
    public void write(JsonWriter out, YearMonth value) throws IOException {
        out.value(YearMonthUtil.toString(value));
    }

    @Override
    public YearMonth read(JsonReader in) throws IOException {
        return YearMonthUtil.fromString(in.nextString());
    }
}