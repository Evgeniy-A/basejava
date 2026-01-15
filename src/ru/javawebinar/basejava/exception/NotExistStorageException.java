package ru.javawebinar.basejava.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super(uuid);
    }

    @Override
    public String getMessage() {
        return "Резюме с UUID " + getUuid() + " нет в базе";
    }
}