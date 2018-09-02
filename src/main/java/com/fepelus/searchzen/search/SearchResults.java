package com.fepelus.searchzen.search;


import com.fepelus.searchzen.storage.Organization;
import com.fepelus.searchzen.storage.Ticket;
import com.fepelus.searchzen.storage.User;

import java.util.List;

/** The lists of the entities of the three types that have matched the search query.
 *
 */
public class SearchResults {
    private List<Organization> organizations;
    private List<Ticket> tickets;
    private List<User> users;

    public SearchResults(List<Organization> organizations, List<Ticket> tickets, List<User> users) {
        this.organizations = organizations;
        this.tickets = tickets;
        this.users = users;
    }

    /** The total number of search hits
     *
     * @return the total number of search hits.
     */
    public int size() {
        return organizations.size() + tickets.size() + users.size();
    }

    /** The organizations that matched the search query
     *
     * @return The List of organizations that matched the search query
     */
    public List<Organization> getOrganizations() {
        return organizations;
    }

    /** The tickets that matched the search query
     *
     * @return The List of tickets that matched the search query
     */
    public List<Ticket> getTickets() {
        return tickets;
    }

    /** The users that matched the search query
     *
     * @return The List of users that matched the search query
     */
    public List<User> getUsers() {
        return users;
    }
}
