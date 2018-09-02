package com.fepelus.searchzen.storage;

import com.fepelus.searchzen.contracts.Tickets;
import com.google.common.io.Resources;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TestTickets {
    @Test
    void shouldProvideTickets() throws JsonParsingException {
        Tickets provider = new JsonTickets("[]");
        assertEquals(0, provider.getAll().size());
    }
    @Test
    void shouldParseOneTicket() throws JsonParsingException {
        Tickets provider = new JsonTickets(oneTicketJson);
        assertEquals(1, provider.getAll().size());
    }

    @Test
    void shouldAcceptInputStream() throws IOException, JsonParsingException {
        Tickets provider = new JsonTickets(new ByteArrayInputStream(oneTicketJson.getBytes("UTF-8")));
        assertEquals(1, provider.getAll().size());
    }

    @Test
    void shouldAcceptStreamOfResourceFile() throws JsonParsingException, IOException {
        InputStream stream = Resources.getResource("tickets.json").openStream();
        Tickets provider = new JsonTickets(stream);
        assertEquals(200, provider.getAll().size());
    }


    @Test
    void shouldRetrieveTicketById() throws JsonParsingException {
        // Given a list of one ticket
        Tickets provider = new JsonTickets(oneTicketJson);

        // When I get the ticket by its ID
        Optional<Ticket> optionalTicket = provider.getById("436bf9b0-1147-4c0a-8439-6f79833bff5b");

        // Then I find a ticket
        assertTrue(optionalTicket.isPresent());

        // And the ticket has the correct subject line
        assertEquals("A Catastrophe in Korea (North)", optionalTicket.get().getSubject());
    }

    @Test
    void shouldNotRetrieveIfIdIsNotPresent() throws JsonParsingException {
        // Given a list of one ticket
        Tickets provider = new JsonTickets(oneTicketJson);

        // When I query by an ID that is not in the data
        Optional<Ticket> optionalTicket = provider.getById("id-not-in-json-resource");

        // Then I do not find a ticket
        assertFalse(optionalTicket.isPresent());
    }


    @Test
    void shouldRetrieveADifferentTicket() throws JsonParsingException, IOException {
        // Given 200 different tickets
        InputStream stream = Resources.getResource("tickets.json").openStream();
        Tickets provider = new JsonTickets(stream);

        // When I select ticket with a known ID
        Optional<Ticket> ticket = provider.getById("1a227508-9f39-427c-8f57-1b72f3fab87c");

        // Then I find a ticket
        assertTrue(ticket.isPresent());

        // And this ticket has the correct subject
        assertEquals("A Catastrophe in Micronesia", ticket.get().getSubject());
    }


    String oneTicketJson = "[{\n" +
            "    \"_id\": \"436bf9b0-1147-4c0a-8439-6f79833bff5b\",\n" +
            "    \"url\": \"http://initech.zendesk.com/api/v2/tickets/436bf9b0-1147-4c0a-8439-6f79833bff5b.json\",\n" +
            "    \"external_id\": \"9210cdc9-4bee-485f-a078-35396cd74063\",\n" +
            "    \"created_at\": \"2016-04-28T11:19:34 -10:00\",\n" +
            "    \"type\": \"incident\",\n" +
            "    \"subject\": \"A Catastrophe in Korea (North)\",\n" +
            "    \"description\": \"Nostrud ad sit velit cupidatat laboris ipsum nisi amet laboris ex exercitation amet et proident. Ipsum fugiat aute dolore tempor nostrud velit ipsum.\",\n" +
            "    \"priority\": \"high\",\n" +
            "    \"status\": \"pending\",\n" +
            "    \"submitter_id\": 38,\n" +
            "    \"assignee_id\": 24,\n" +
            "    \"organization_id\": 116,\n" +
            "    \"tags\": [\n" +
            "      \"Ohio\",\n" +
            "      \"Pennsylvania\",\n" +
            "      \"American Samoa\",\n" +
            "      \"Northern Mariana Islands\"\n" +
            "    ],\n" +
            "    \"has_incidents\": false,\n" +
            "    \"due_at\": \"2016-07-31T02:37:50 -10:00\",\n" +
            "    \"via\": \"web\"\n" +
            "  }]";

}
