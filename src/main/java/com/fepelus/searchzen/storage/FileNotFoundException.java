package com.fepelus.searchzen.storage;

public class FileNotFoundException extends Exception {
    public FileNotFoundException(String helpMessage, java.io.FileNotFoundException e) {
        super(helpMessage, e);
    }
}
