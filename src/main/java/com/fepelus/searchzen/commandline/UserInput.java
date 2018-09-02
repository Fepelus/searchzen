package com.fepelus.searchzen.commandline;

import com.fepelus.searchzen.storage.FileNotFoundException;
import com.fepelus.searchzen.storage.SearchQuery;
import com.fepelus.searchzen.storage.Streams;

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

    /** The <code>InputStream</code> of the organizations.json file.
     *
     * @return The <code>InputStream</code> of the content of the organizations.json file.
     * @throws FileNotFoundException if the user has configured a directory that does not have an organizations.json file in it
     */
    @Override
    public InputStream organizationsJsonStream() throws FileNotFoundException {
        if (!includeOrganizations) {
            // Don’t load it if we’re not searching it
            return emptyStream();
        }
        return streamSource.getStream("organizations.json");
    }

    /** The <code>InputStream</code> of the tickets.json file.
     *
     * @return The <code>InputStream</code> of the content of the tickets.json file.
     * @throws FileNotFoundException if the user has configured a directory that does not have an tickets.json file in it
     */
    @Override
    public InputStream ticketsJsonStream() throws FileNotFoundException {
        if (!includeTickets) {
            return emptyStream();
        }
        return streamSource.getStream("tickets.json");
    }

    /** The <code>InputStream</code> of the users.json file.
     *
     * @return The <code>InputStream</code> of the content of the users.json file.
     * @throws FileNotFoundException if the user has configured a directory that does not have an users.json file in it
     */
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

    /** The search term is going to be matched on all attributes
     *
     * @return <code>false</code> if the user has specified that the search is to be matched only on certain, specified attributes, <code>true</code> otherwise
     */
    public boolean searchAllAttributes() {
        return ! this.attributes.isPresent();
    }

    /** List of the attributes that the search term is going to be matched against
     *
     * If this list is not empty, then no entity will be included in the <code>SearchResults</code>
     * unless the <code>searchTerm</code> matches one of the attributes in this list.
     *
     * @return List of the attributes that the search term is going to be matched against.
     */
    public List<String> limitToAttributes() {
        return this.attributes.orElse(Arrays.asList());
    }
}


interface StreamSource {
    InputStream getStream(String filename) throws FileNotFoundException;
}

/** Get the <code>InputStream</code>s from the files hard-coded in the jar file
 *
 */
class ResourcesSource implements StreamSource {

    @Override
    public InputStream getStream(String filename) {
        return ResourcesSource.class.getClassLoader().getResourceAsStream(filename);
    }
}

/** Get the <code>InputStream</code>s from the directory specified by the user
 *
 */
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