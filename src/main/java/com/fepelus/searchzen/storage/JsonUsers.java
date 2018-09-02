package com.fepelus.searchzen.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.fepelus.searchzen.contracts.SearchQuery;
import com.fepelus.searchzen.contracts.Users;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class JsonUsers implements Users {
    private List<User> users;

    JsonUsers(String json) throws JsonParsingException {
        this(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)));
    }

    public JsonUsers(InputStream stream) throws JsonParsingException {
        ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
        try {
            users = objectMapper.readValue(stream, new TypeReference<List<User>>(){});
        } catch (IOException e) {
            throw new JsonParsingException("Could not parse the users.json file", e);
        }
    }

    @Override
    public List<User> getAll() {
        return Collections.unmodifiableList(users);
    }

    @Override
    public Optional<User> getById(long id) {
        return users.stream().filter(user -> user.getId() == id).findFirst();
    }

    @Override
    public List<User> search(SearchQuery query) {
        return users.stream()
                .filter(user -> user.matches(query))
                .collect(Collectors.toList());
    }
}

