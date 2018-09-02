package com.fepelus.searchzen.contracts;

import com.fepelus.searchzen.storage.Organization;

import java.util.List;
import java.util.Optional;

public interface Organizations {
    List<Organization> getAll();

    Optional<Organization> getById(long id);

    List<Organization> search(SearchQuery query);
}
