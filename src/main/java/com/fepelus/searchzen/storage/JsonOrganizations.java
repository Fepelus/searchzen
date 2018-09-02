package com.fepelus.searchzen.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.fepelus.searchzen.contracts.Organizations;
import com.fepelus.searchzen.contracts.SearchQuery;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class JsonOrganizations implements Organizations {
    private List<Organization> organizations;

    JsonOrganizations(String json) throws JsonParsingException {
        this(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)));
    }

    JsonOrganizations(InputStream stream) throws JsonParsingException {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
        try {
            organizations = objectMapper.readValue(stream, new TypeReference<List<Organization>>(){});
        } catch (IOException e) {
            throw new JsonParsingException("Could not parse the organizations.json file", e);
        }
    }

    @Override
    public List<Organization> getAll() {
        return organizations;
    }

    @Override
    public List<Organization> search(SearchQuery query) {
        return organizations.stream()
                .filter(organization -> organization.matches(query))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Organization> getById(long id) {
        return organizations.stream().filter(org -> org.getId() == id).findFirst();
    }
}
