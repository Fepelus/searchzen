package com.fepelus.searchzen.contracts;

import com.fepelus.searchzen.storage.User;

import java.util.List;
import java.util.Optional;

public interface Users {
    List<User> getAll();

    Optional<User> getById(long id);

    List<User> search(SearchQuery query);
}
