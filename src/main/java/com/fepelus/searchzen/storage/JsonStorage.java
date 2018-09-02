package com.fepelus.searchzen.storage;

import com.fepelus.searchzen.contracts.*;
import com.fepelus.searchzen.search.SearchResults;

import java.util.Optional;

public class JsonStorage implements Storage, Searchable {

    private final Streams config;
    Users users;
    Tickets tickets;
    Organizations organizations;

    public JsonStorage(Streams config)  {
        this.config = config;
    }

    public JsonStorage load() throws JsonParsingException, FileNotFoundException {
            users = new JsonUsers(config.usersJsonStream());
            tickets = new JsonTickets(config.ticketsJsonStream());
            organizations = new JsonOrganizations(config.organizationsJsonStream());
            return this;
    }



    @Override
    public SearchResults search(SearchQuery query) {
        var output = new SearchResults();
        output.matchingOrganizations(organizations.search(query));
        output.matchingTickets(tickets.search(query));
        output.matchingUsers(users.search(query));
        return output;
    }


    @Override
    public Optional<Organization> getOrganizationById(long organizationId) {
        return organizations.getById(organizationId);
    }

    @Override
    public Optional<User> getUserById(long userId) {
        return users.getById(userId);
    }
}

