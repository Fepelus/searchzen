package com.fepelus.searchzen.commandline;

import org.apache.commons.cli.ParseException;

public class ParseCommandLineException extends Exception {
    public ParseCommandLineException(String helpMessage, ParseException e) {
        super(helpMessage, e);
    }
}
