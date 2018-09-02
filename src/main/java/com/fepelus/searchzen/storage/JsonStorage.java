package com.fepelus.searchzen.storage;

import com.fepelus.searchzen.format.Storage;
import com.fepelus.searchzen.search.SearchResults;

import java.util.Optional;

/** Collects the lists of users, tickets and organizations.
 *
 */
public class JsonStorage implements Storage {

    Users users;
    Tickets tickets;
    Organizations organizations;

    JsonStorage(Users users, Tickets tickets, Organizations organizations) {
        this.users = users;
        this.tickets = tickets;
        this.organizations = organizations;
    }

    /** Search all the three different entity types
     *
     * @return SearchResults object that holds the entities that match the query
     */
    public SearchResults search(SearchQuery query) {
        return new SearchResults(
            organizations.search(query),
            tickets.search(query),
            users.search(query)
        );
    }


    /** Get organization by id
     *
     * @param organizationId
     * @return optional of the Organization with the given ID or Optional.empty if there is none
     */
    @Override
    public Optional<Organization> getOrganizationById(long organizationId) {
        return organizations.getById(organizationId);
    }

    /** Get user by id
     *
     * @param userId
     * @return optional of the User with the given ID or Optional.empty if there is none
     */
    @Override
    public Optional<User> getUserById(long userId) {
        return users.getById(userId);
    }
}

