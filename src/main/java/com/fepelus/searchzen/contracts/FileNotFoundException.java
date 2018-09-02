package com.fepelus.searchzen.contracts;

public class FileNotFoundException extends Exception {
    public FileNotFoundException(String helpMessage, java.io.FileNotFoundException e) {
        super(helpMessage, e);
    }
}
