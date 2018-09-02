package com.fepelus.searchzen.search;

import com.fepelus.searchzen.UserInputBuilder;
import com.fepelus.searchzen.commandline.UserInput;
import com.fepelus.searchzen.contracts.FileNotFoundException;
import com.fepelus.searchzen.contracts.Searchable;
import com.fepelus.searchzen.storage.JsonParsingException;
import com.fepelus.searchzen.storage.JsonStorage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestSearch {
    @Test
    void shouldReturnEverythingThatMatchesWest() throws JsonParsingException, FileNotFoundException {
        UserInput config = new UserInputBuilder().searchTerm("West").build();
        Searchable storage = new JsonStorage(config).load();
        SearchResults results = storage.search(config);

        assertEquals(1, results.size());
    }

    @Test
    void shouldReturnEverythingThatMatchesGobbledigook() throws JsonParsingException, FileNotFoundException {
        UserInput config = new UserInputBuilder().searchTerm("Gobbledigook").build();
        Searchable storage = new JsonStorage(config).load();
        SearchResults results = storage.search(config);

        assertEquals(0, results.size());
    }
    @Test
    void shouldReturnEverythingThatMatchesAnIndex() throws JsonParsingException, FileNotFoundException {
        UserInput config = new UserInputBuilder().searchTerm("2").build();
        Searchable storage = new JsonStorage(config).load();
        SearchResults results = storage.search(config);

        assertEquals(3, results.size());
    }
    @Test
    void shouldReturnEverythingThatMatchesABoolean() throws JsonParsingException, FileNotFoundException {
        UserInput config = new UserInputBuilder().searchTerm("true").build();
        Searchable storage = new JsonStorage(config).load();
        SearchResults results = storage.search(config);

        assertEquals(172, results.size());
    }
    @Test
    void shouldReturnOnlyTicketsThatMatchABoolean() throws JsonParsingException, FileNotFoundException {
        UserInput config = new UserInputBuilder().includeTickets(true).searchTerm("true").build();
        Searchable storage = new JsonStorage(config).load();
        SearchResults results = storage.search(config);
        assertEquals(99, results.size());
    }
    @Test
    void shouldOnlyReturnMatchesOfSpecifiedAttribute() throws JsonParsingException, FileNotFoundException {
        UserInput config = new UserInputBuilder().attributes("active").searchTerm("true").build();
        Searchable storage = new JsonStorage(config).load();
        SearchResults results = storage.search(config);
        assertEquals(39, results.size());
    }
    @Test
    void shouldReturnMoreMatchesIfSpecifyMoreAttributes() throws JsonParsingException, FileNotFoundException {
        UserInput config = new UserInputBuilder().attributes("active", "has_incidents").searchTerm("true").build();
        Searchable storage = new JsonStorage(config).load();
        SearchResults results = storage.search(config);
        assertEquals(138, results.size());
    }
    @Test
    void shouldReturnMatchOfTag() throws JsonParsingException, FileNotFoundException {
        UserInput config = new UserInputBuilder().attributes("tags").searchTerm("West").build();
        Searchable storage = new JsonStorage(config).load();
        SearchResults results = storage.search(config);
        assertEquals(1, results.size());
    }
    @Test
    void shouldNotReturnMatchOfSomethingOtherThanTag() throws JsonParsingException, FileNotFoundException {
        UserInput config = new UserInputBuilder().attributes("name").searchTerm("West").build();
        Searchable storage = new JsonStorage(config).load();
        SearchResults results = storage.search(config);
        assertEquals(0, results.size());
    }

}

