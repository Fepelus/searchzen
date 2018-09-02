package com.fepelus.searchzen.search;


import com.fepelus.searchzen.storage.Organization;
import com.fepelus.searchzen.storage.Ticket;
import com.fepelus.searchzen.storage.User;

import java.util.List;

public class SearchResults {
    private List<Organization> organizations;
    private List<Ticket> tickets;
    private List<User> users;

    public int size() {
        return organizations.size() + tickets.size() + users.size();
    }

    public void matchingOrganizations(List<Organization> matching) {
        this.organizations = matching;
    }

    public void matchingTickets(List<Ticket> matching) {
        this.tickets = matching;
    }

    public void matchingUsers(List<User> matching) {
        this.users = matching;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public List<User> getUsers() {
        return users;
    }
}
