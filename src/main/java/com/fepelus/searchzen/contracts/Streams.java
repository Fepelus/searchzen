package com.fepelus.searchzen.contracts;

import java.io.InputStream;

public interface Streams {
    InputStream organizationsJsonStream() throws FileNotFoundException;
    InputStream ticketsJsonStream() throws FileNotFoundException;
    InputStream usersJsonStream() throws FileNotFoundException;
}
