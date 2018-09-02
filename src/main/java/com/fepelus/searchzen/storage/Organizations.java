package com.fepelus.searchzen.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class Organizations {
    private List<Organization> organizations;

    Organizations(String json) throws JsonParsingException {
        this(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)));
    }

    Organizations(InputStream stream) throws JsonParsingException {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
        try {
            organizations = objectMapper.readValue(stream, new TypeReference<List<Organization>>(){});
        } catch (IOException e) {
            throw new JsonParsingException("Could not parse the organizations.json file", e);
        }
    }

    // for testing
    List<Organization> getAll() {
        return organizations;
    }

    public List<Organization> search(SearchQuery query) {
        return organizations.stream()
                .filter(organization -> organization.matches(query))
                .collect(Collectors.toList());
    }

    Optional<Organization> getById(long id) {
        return organizations.stream().filter(org -> org.getId() == id).findFirst();
    }
}
