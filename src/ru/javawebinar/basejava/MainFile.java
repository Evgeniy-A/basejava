package ru.javawebinar.basejava;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        File dir = new File("./src/ru");
        printDirectoryTree(dir);

    }

    public static void printDirectoryTree(File dir) {
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println(file.getName().toUpperCase());
                printDirectoryTree(file);
            } else {
                System.out.println(file.getName());
            }
        }
    }
}