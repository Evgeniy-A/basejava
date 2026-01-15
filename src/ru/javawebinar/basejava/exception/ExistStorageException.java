package ru.javawebinar.basejava.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super(uuid);
    }

    @Override
    public String getMessage() {
        return "Резюме с UUID: " + getUuid() + " уже есть в базе";
    }
}