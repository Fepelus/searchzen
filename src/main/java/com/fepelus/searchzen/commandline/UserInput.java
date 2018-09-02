package com.fepelus.searchzen.commandline;

import com.fepelus.searchzen.contracts.FileNotFoundException;
import com.fepelus.searchzen.contracts.SearchQuery;
import com.fepelus.searchzen.contracts.Streams;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/** The intentions of the user
 *
 * Converts data from the command-line parser to formats useful to the application.
 *
 */
public class UserInput implements Streams, SearchQuery {
    private boolean includeOrganizations;
    private boolean includeTickets;
    private boolean includeUsers;
    private final StreamSource streamSource;
    private final Optional<List<String>> attributes;
    private final String searchTerm;

    public UserInput(boolean includeOrganizations, boolean includeTickets, boolean includeUsers, String directory, String[] attributes, String searchTerm) {
        this.includeOrganizations = includeOrganizations;
        this.includeTickets = includeTickets;
        this.includeUsers = includeUsers;
        this.streamSource = directory == null ? new ResourcesSource() : new DirectorySource(directory);
        this.attributes = toOptionalOfList(attributes);
        this.searchTerm = searchTerm;

        // If the user does not specify any include flags
        // then we should include all in the final results, not nothing
        checkWhetherAllIncludesAreFalseAndFix();
    }

    private static Optional<List<String>> toOptionalOfList(String[] attributes) {
        if (attributes == null) {
            return Optional.empty();
        }
        if (attributes.length == 0) {
            return Optional.empty();
        }
        return Optional.of(Arrays.asList(attributes));
    }

    public boolean includeOrganizations() {
        return includeOrganizations;
    }

    public boolean includeTickets() {
        return includeTickets;
    }

    public boolean includeUsers() {
        return includeUsers;
    }

    public String searchTerm() {
        return searchTerm;
    }

    @Override
    public InputStream organizationsJsonStream() throws FileNotFoundException {
        if (!includeOrganizations) {
            // Don’t load it if we’re not searching it
            return emptyStream();
        }
        return streamSource.getStream("organizations.json");
    }

    @Override
    public InputStream ticketsJsonStream() throws FileNotFoundException {
        if (!includeTickets) {
            return emptyStream();
        }
        return streamSource.getStream("tickets.json");
    }

    @Override
    public InputStream usersJsonStream() throws FileNotFoundException {
        if (!includeUsers) {
            return emptyStream();
        }
        return streamSource.getStream("users.json");
    }

    private static InputStream emptyStream() {
        return new ByteArrayInputStream("[]".getBytes(StandardCharsets.UTF_8));
    }

    private void checkWhetherAllIncludesAreFalseAndFix() {
        if (UserInput.includeNothing(includeOrganizations, includeTickets, includeUsers)) {
            this.includeOrganizations = true;
            this.includeTickets = true;
            this.includeUsers = true;
        }
    }

    static private boolean includeNothing(boolean includeOrganizations, boolean includeTickets, boolean includeUsers) {
        return !(includeOrganizations || includeTickets || includeUsers);
    }

    public boolean searchAllAttributes() {
        return ! this.attributes.isPresent();
    }

    public List<String> limitToAttributes() {
        return this.attributes.orElse(Arrays.asList());
    }
}


interface StreamSource {
    InputStream getStream(String filename) throws FileNotFoundException;
}

class ResourcesSource implements StreamSource {

    @Override
    public InputStream getStream(String filename) {
        return ResourcesSource.class.getClassLoader().getResourceAsStream(filename);
    }
}

class DirectorySource implements StreamSource {
    private final String directory;

    DirectorySource(String directory) {
        this.directory = directory;
    }

    @Override
    public InputStream getStream(String filename) throws FileNotFoundException {
        String absoluteFilename = directory + File.separator + filename;
        try {
            return new FileInputStream(new File(absoluteFilename));
        } catch (java.io.FileNotFoundException e) {
            throw new FileNotFoundException("Could not load the file " + filename, e);
        }

    }
}