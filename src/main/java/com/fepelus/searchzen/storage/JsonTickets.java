package com.fepelus.searchzen.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.fepelus.searchzen.contracts.SearchQuery;
import com.fepelus.searchzen.contracts.Tickets;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class JsonTickets implements Tickets {
    private List<Ticket> tickets;

    JsonTickets(String json) throws JsonParsingException {
        this(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)));
    }

    public JsonTickets(InputStream stream) throws JsonParsingException {
        ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
        try {
            tickets = objectMapper.readValue(stream, new TypeReference<List<Ticket>>(){});
        } catch (IOException e) {
            throw new JsonParsingException("Could not parse the tickets.json file", e);
        }
    }

    @Override
    public List<Ticket> getAll() {
        return Collections.unmodifiableList(tickets);
    }

    @Override
    public Optional<Ticket> getById(String id) {
        return tickets.stream().filter(ticket -> ticket.getId().equals(id)).findFirst();
    }

    @Override
    public List<Ticket> search(SearchQuery query) {
        return tickets.stream()
                .filter(ticket -> ticket.matches(query))
                .collect(Collectors.toList());
    }
}
