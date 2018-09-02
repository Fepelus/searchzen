package com.fepelus.searchzen.format;

import com.fepelus.searchzen.contracts.Storage;
import com.fepelus.searchzen.storage.Ticket;

import java.util.Arrays;
import java.util.stream.Collectors;

class FormattedTicket {
    private final Ticket ticket;
    private final Storage storage;

    public FormattedTicket(Ticket ticket, Storage storage) {
        this.ticket = ticket;
        this.storage = storage;
    }


    @Override
    public String toString() {
        return
            "_id: " + ticket.getId() + "\n" +
            "url: " + ticket.getUrl() + "\n" +
            "external_id: " + ticket.getExternalId() + "\n" +
            "created_at: " + ticket.getCreatedAt() + "\n" +
            "type: " + ticket.getType() + "\n" +
            "subject: " + ticket.getSubject() + "\n" +
            "description: " + ticket.getDescription() + "\n" +
            "priority: " + ticket.getPriority() + "\n" +
            "status: " + ticket.getStatus() + "\n" +
            "submitter: " + storage.getUserById(ticket.getSubmitterId()).map(user -> "\n  " + new FormattedUser(user, storage).compact()).orElse("") + "\n" +
            "assignee: " + storage.getUserById(ticket.getAssigneeId()).map(user -> "\n  " + new FormattedUser(user, storage).compact()).orElse("") + "\n" +
            "organization: " + storage.getOrganizationById(ticket.getOrganizationId()).map(org -> "\n  " + new FormattedOrganisation(org).compact()).orElse("") + "\n" +
            "has_incidents: " + ticket.getHasIncidents() + "\n" +
            "due_at: " + ticket.getDueAt() + "\n" +
            "via: " + ticket.getVia() + "\n" +
            "tags: " + ticket.getTags() + "\n";
    }

}
