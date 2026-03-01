package ru.javawebinar.basejava;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        File dir = new File("./src/ru");
        printFilesRecursively(dir);

    }

    public static void printFilesRecursively(File dir) {
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println(file.getName().toUpperCase());
                printFilesRecursively(file);
            } else {
                System.out.println(file.getName());
            }
        }
    }
}