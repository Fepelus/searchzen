package com.fepelus.searchzen.search;

import com.fepelus.searchzen.UserInputBuilder;
import com.fepelus.searchzen.commandline.UserInput;
import com.fepelus.searchzen.storage.FileNotFoundException;
import com.fepelus.searchzen.storage.JsonParsingException;
import com.fepelus.searchzen.storage.JsonStorage;
import com.fepelus.searchzen.storage.LoadingJsonStorage;
import com.google.common.io.Resources;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestSearch {
    @Test
    void shouldReturnEverythingThatMatchesWest() throws JsonParsingException, FileNotFoundException {
        UserInput config = new UserInputBuilder().searchTerm("West").build();
        JsonStorage storage = new LoadingJsonStorage(config).load();
        SearchResults results = storage.search(config);

        assertEquals(1, results.size());
    }

    @Test
    void shouldReturnOnlyCaseSensitiveMatches() throws JsonParsingException, FileNotFoundException {
        UserInput config = new UserInputBuilder().searchTerm("west").build();
        JsonStorage storage = new LoadingJsonStorage(config).load();
        SearchResults results = storage.search(config);

        assertEquals(0, results.size());
    }

    @Test
    void shouldReturnEverythingThatMatchesGobbledigook() throws JsonParsingException, FileNotFoundException {
        UserInput config = new UserInputBuilder().searchTerm("Gobbledigook").build();
        JsonStorage storage = new LoadingJsonStorage(config).load();
        SearchResults results = storage.search(config);

        assertEquals(0, results.size());
    }
    @Test
    void shouldReturnEverythingThatMatchesAnIndex() throws JsonParsingException, FileNotFoundException {
        UserInput config = new UserInputBuilder().searchTerm("2").build();
        JsonStorage storage = new LoadingJsonStorage(config).load();
        SearchResults results = storage.search(config);

        assertEquals(3, results.size());
    }
    @Test
    void shouldReturnEverythingThatMatchesABoolean() throws JsonParsingException, FileNotFoundException {
        UserInput config = new UserInputBuilder().searchTerm("true").build();
        JsonStorage storage = new LoadingJsonStorage(config).load();
        SearchResults results = storage.search(config);

        assertEquals(172, results.size());
    }
    @Test
    void shouldReturnOnlyTicketsThatMatchABoolean() throws JsonParsingException, FileNotFoundException {
        UserInput config = new UserInputBuilder().includeTickets(true).searchTerm("true").build();
        JsonStorage storage = new LoadingJsonStorage(config).load();
        SearchResults results = storage.search(config);
        assertEquals(99, results.size());
    }
    @Test
    void shouldOnlyReturnMatchesOfSpecifiedAttribute() throws JsonParsingException, FileNotFoundException {
        UserInput config = new UserInputBuilder().attributes("active").searchTerm("true").build();
        JsonStorage storage = new LoadingJsonStorage(config).load();
        SearchResults results = storage.search(config);
        assertEquals(39, results.size());
    }
    @Test
    void shouldReturnMoreMatchesIfSpecifyMoreAttributes() throws JsonParsingException, FileNotFoundException {
        UserInput config = new UserInputBuilder().attributes("active", "has_incidents").searchTerm("true").build();
        JsonStorage storage = new LoadingJsonStorage(config).load();
        SearchResults results = storage.search(config);
        assertEquals(138, results.size());
    }
    @Test
    void shouldReturnMatchOfTag() throws JsonParsingException, FileNotFoundException {
        UserInput config = new UserInputBuilder().attributes("tags").searchTerm("West").build();
        JsonStorage storage = new LoadingJsonStorage(config).load();
        SearchResults results = storage.search(config);
        assertEquals(1, results.size());
    }
    @Test
    void shouldNotReturnMatchOfSomethingOtherThanTag() throws JsonParsingException, FileNotFoundException {
        UserInput config = new UserInputBuilder().attributes("name").searchTerm("West").build();
        JsonStorage storage = new LoadingJsonStorage(config).load();
        SearchResults results = storage.search(config);
        assertEquals(0, results.size());
    }

    @Test
    void shouldSearchForEmptyString() throws JsonParsingException, FileNotFoundException {
        // Given a directory that has a tickets.json file with  a ticket where the subject is "" — the empty string
        String directoryFlagPath = Resources.getResource("directoryFlag").getPath();

        // When I search for an empty string
        UserInput config = new UserInputBuilder().directory(directoryFlagPath).searchTerm("").build();
        JsonStorage storage = new LoadingJsonStorage(config).load();
        SearchResults results = storage.search(config);

        // Then there is one match
        assertEquals(1, results.size());
    }

    @Test
    void shouldNotFindAnythingIfSearchForEmptyStringOnADifferentAttribute() throws JsonParsingException, FileNotFoundException {
        // Given a directory that has a tickets.json file with  a ticket where the subject is "" — the empty string
        String directoryFlagPath = Resources.getResource("directoryFlag").getPath();

        // When I search for an empty string but limit matches to an attribute that is not empty in the data file
        UserInput config = new UserInputBuilder().directory(directoryFlagPath).attributes("status").searchTerm("").build();
        JsonStorage storage = new LoadingJsonStorage(config).load();
        SearchResults results = storage.search(config);

        // Then there are no matches
        assertEquals(0, results.size());
    }

}

