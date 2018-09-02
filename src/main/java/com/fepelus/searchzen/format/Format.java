package com.fepelus.searchzen.format;

import com.fepelus.searchzen.contracts.Storage;
import com.fepelus.searchzen.search.SearchResults;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Format {
    private Storage storage;

    public Format(Storage storage) {
        this.storage = storage;
    }

    public String format(SearchResults results) {
        String organizations = results.getOrganizations().stream()
                .map(org -> new FormattedOrganisation(org).toString())
                .collect(Collectors.joining("\n"));

        String users = results.getUsers().stream()
                .map(user -> new FormattedUser(user, storage).toString())
                .collect(Collectors.joining("\n"));

        String tickets = results.getTickets().stream()
                .map(ticket -> new FormattedTicket(ticket, storage).toString())
                .collect(Collectors.joining("\n"));

        return ((organizations.isEmpty()? "" : organizations + "\n") +
               (users.isEmpty()? "" : users + "\n") + tickets).trim();
    }

}
