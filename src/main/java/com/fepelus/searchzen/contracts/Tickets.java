package com.fepelus.searchzen.contracts;

import com.fepelus.searchzen.storage.Ticket;

import java.util.List;
import java.util.Optional;

public interface Tickets {
    List<Ticket> getAll();

    Optional<Ticket> getById(String id);

    List<Ticket> search(SearchQuery query);
}
