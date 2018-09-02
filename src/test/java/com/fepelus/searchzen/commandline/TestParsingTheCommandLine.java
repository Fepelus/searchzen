package com.fepelus.searchzen.commandline;

import com.fepelus.searchzen.contracts.FileNotFoundException;
import com.google.common.io.Resources;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TestParsingTheCommandLine {
    @Test
    void shouldThrowIfGivenNoArgument()  {
        assertThrows(ParseCommandLineException.class, () ->new CommandLine(new String[]{}).parse());
    }

    @Test
    void shouldThrowAnExceptionWithTheUsageMessage()  {
        String helpMessage = "usage: searchzen [options] searchterm\n" +
              "  -a,--attribute <arg>  Which attribute(s) to search for\n" +
              "  -d,--directory <arg>  Directory that holds the three json files\n" +
              "  -o,--organizations    Match organizations\n" +
              "  -t,--tickets          Match tickets\n" +
              "  -u,--users            Match users\n";

        try {
            new CommandLine(new String[]{}).parse();
            fail("Should have thrown before this");
        } catch (ParseCommandLineException e) {
            assertEquals(helpMessage, e.getMessage());
        }
    }


    @Test
    void shouldDefaultToSearchingForAllTypes() throws ParseCommandLineException {
        UserInput userInput = new CommandLine(new String[]{"searchterm"}).parse();
        assertTrue(userInput.includeOrganizations());
        assertTrue(userInput.includeTickets());
        assertTrue(userInput.includeUsers());
    }

    @Test
    void shouldIncludeOnlyUsersIfUserFlag() throws ParseCommandLineException {
        var userInput = new CommandLine(new String[]{"--users", "searchterm"}).parse();
        assertFalse(userInput.includeOrganizations());
        assertFalse(userInput.includeTickets());
        assertTrue(userInput.includeUsers());
    }

    @Test
    void shouldThrowIfGivenFlagButNoSearchterm()  {
        assertThrows(ParseCommandLineException.class, () ->new CommandLine(new String[]{"--users"}).parse());
    }

    @Test
    void shouldIncludeTicketsAndUsersIfTwoFlags() throws ParseCommandLineException {
        var userInput = new CommandLine(new String[]{"--users", "--tickets", "searchterm"}).parse();
        assertFalse(userInput.includeOrganizations());
        assertTrue(userInput.includeTickets());
        assertTrue(userInput.includeUsers());
    }

    @Test
    void shouldHaveASearchTerm() throws ParseCommandLineException {
        var userInput = new CommandLine(new String[]{"search"}).parse();
        assertEquals("search", userInput.searchTerm());
    }

    @Test
    void shouldHaveAnEmptySearchTerm() throws ParseCommandLineException {
        var userInput = new CommandLine(new String[]{""}).parse();
        assertEquals("", userInput.searchTerm());
    }


    @Test
    void shouldIgnoreAllButTheFirstSearchTerm() throws ParseCommandLineException {
        var userInput = new CommandLine(new String[]{"search", "another"}).parse();
        assertEquals("search", userInput.searchTerm());
    }


    @Test
    void shouldDefaultOrgStreamToResources() throws ParseCommandLineException, IOException, FileNotFoundException {
        var userInput = new CommandLine(new String[]{"searchterm"}).parse();
        byte[] allBytes = userInput.organizationsJsonStream().readAllBytes();
        assertEquals(11973, allBytes.length); // size from `ls -l src/test/resources`
    }

    @Test
    void shouldDefaultTicketStreamToResource() throws ParseCommandLineException, IOException, FileNotFoundException {
        var userInput = new CommandLine(new String[]{"searchterm"}).parse();
        byte[] allBytes = userInput.ticketsJsonStream().readAllBytes();
        assertEquals(158756, allBytes.length);
    }

    @Test
    void shouldDefaultUserStreamToResource() throws ParseCommandLineException, IOException, FileNotFoundException {
        var userInput = new CommandLine(new String[]{"searchterm"}).parse();
        byte[] allBytes = userInput.usersJsonStream().readAllBytes();
        assertEquals(51685, allBytes.length);
    }

    @Test
    void shouldGetUserStreamFromDirectoryIfSpecified() throws FileNotFoundException, ParseCommandLineException, IOException {
        var userInput = new CommandLine(new String[]{"--directory", directoryFlagPath(), "searchterm"}).parse();
        byte[] allBytes = userInput.usersJsonStream().readAllBytes();
        assertEquals(717, allBytes.length); // size from `ls -l src/test/resources/directoryFlag`
    }

    @Test
    void shouldThrowIfSpecifyingADirectoryThatHasNoJsonFiles() throws ParseCommandLineException {
        var userInput = new CommandLine(new String[]{"--directory", "/tmp", "searchterm"}).parse();
        assertThrows(FileNotFoundException.class, () -> userInput.organizationsJsonStream());
    }

    @Test
    void shouldDefaultToSearchForAllAttributes() throws ParseCommandLineException {
        var userInput = new CommandLine(new String[]{"searchterm"}).parse();
        assertTrue(userInput.searchAllAttributes());
        assertEquals(0, userInput.limitToAttributes().size());
    }

    @Test
    void shouldSpecifyWhichAttributesToSearchFor() throws ParseCommandLineException {
        var userInput = new CommandLine(new String[]{"--attribute", "name", "--", "searchterm"}).parse();
        assertFalse(userInput.searchAllAttributes());
        assertEquals(1, userInput.limitToAttributes().size());
    }

    @Test
    void shouldAllowMoreThanOneAttribute() throws ParseCommandLineException {
        var userInput = new CommandLine(new String[]{"--attribute", "name", "subject", "--", "searchterm"}).parse();
        assertFalse(userInput.searchAllAttributes());
        assertEquals(2, userInput.limitToAttributes().size());
    }

    @Test
    void shouldNotRequireDashesIfWeAddAnotherParameter() throws ParseCommandLineException {
        var userInput = new CommandLine(new String[]{"--attribute", "name", "subject", "--users", "searchterm"}).parse();
        assertFalse(userInput.searchAllAttributes());
        assertEquals(2, userInput.limitToAttributes().size());
    }

    // Return the absolute pathname of the test/resources/directoryFlag directory
    private String directoryFlagPath() {
        return Resources.getResource("directoryFlag").getPath();
    }
}
