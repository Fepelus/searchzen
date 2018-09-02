package com.fepelus.searchzen.storage;

import java.io.IOException;

public class JsonParsingException extends Throwable {
    public JsonParsingException(String s, IOException e) {
        super(s, e);
    }
}
