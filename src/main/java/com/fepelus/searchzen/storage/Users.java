package com.fepelus.searchzen.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class Users {
    private List<User> users;

    Users(String json) throws JsonParsingException {
        this(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)));
    }

    Users(InputStream stream) throws JsonParsingException {
        ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
        try {
            users = objectMapper.readValue(stream, new TypeReference<List<User>>(){});
        } catch (IOException e) {
            throw new JsonParsingException("Could not parse the users.json file", e);
        }
    }

    List<User> getAll() {
        return Collections.unmodifiableList(users);
    }

    Optional<User> getById(long id) {
        return users.stream().filter(user -> user.getId() == id).findFirst();
    }

    public List<User> search(SearchQuery query) {
        return users.stream()
                .filter(user -> user.matches(query))
                .collect(Collectors.toList());
    }
}

