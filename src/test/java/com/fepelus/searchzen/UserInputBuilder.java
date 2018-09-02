package com.fepelus.searchzen;

import com.fepelus.searchzen.commandline.UserInput;

public class UserInputBuilder {
    private boolean includeOrganizations = false;
    private boolean includeTickets = false;
    private boolean includeUsers = false;
    private String directory = null;
    private String[] attributes = null;
    private String searchTerm = "";

    public UserInputBuilder includeOrganisations(boolean includeOrganizations) {
        this.includeOrganizations = includeOrganizations;
        return this;
    }
    public UserInputBuilder includeTickets(boolean includeTickets) {
        this.includeTickets = includeTickets;
        return this;
    }
    public UserInputBuilder includeUsers(boolean includeUsers) {
        this.includeUsers = includeUsers;
        return this;
    }
    public UserInputBuilder directory(String directory) {
        this.directory = directory;
        return this;
    }
    public UserInputBuilder attributes(String... attributes) {
        this.attributes = attributes;
        return this;
    }
    public UserInputBuilder searchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
        return this;
    }

    public UserInput build() {
        return new UserInput(includeOrganizations, includeTickets, includeUsers, directory, attributes, searchTerm);
    }

}
