package com.fepelus.searchzen.storage;

import com.fepelus.searchzen.contracts.Organizations;
import com.google.common.io.Resources;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TestOrganizations {
    @Test
    void shouldProvideorganizations() throws JsonParsingException {
        Organizations provider = new JsonOrganizations("[]");
        assertEquals(0, provider.getAll().size());
    }

    @Test
    void shouldParseOneorganization() throws JsonParsingException {
        Organizations provider = new JsonOrganizations(oneOrganizationJson);
        assertEquals(1, provider.getAll().size());
    }

    @Test
    void shouldAcceptInputStream() throws IOException, JsonParsingException {
        Organizations provider = new JsonOrganizations(new ByteArrayInputStream(oneOrganizationJson.getBytes("UTF-8")));
        assertEquals(1, provider.getAll().size());
    }

    @Test
    void shouldAcceptStreamOfResourceFile() throws IOException, JsonParsingException {
        InputStream stream = Resources.getResource("organizations.json").openStream();
        Organizations provider = new JsonOrganizations(stream);
        assertEquals(25, provider.getAll().size());
    }

    @Test
    void shouldRetrieveOrganizationById() throws JsonParsingException {
        // Given a list of one organization
        Organizations provider = new JsonOrganizations(oneOrganizationJson);

        // When I get the organization by her ID
        Optional<Organization> optionalOrganization = provider.getById(101);

        // Then I find a organization
        assertTrue(optionalOrganization.isPresent());

        // And the organization has the correct name
        assertEquals("Enthaze", optionalOrganization.get().getName());
    }

    @Test
    void shouldNotRetrieveIfIdIsNotPresent() throws JsonParsingException {
        // Given a list of one organization
        Organizations provider = new JsonOrganizations(oneOrganizationJson);

        // When I query for an ID that is not present in the data
        Optional<Organization> optionalOrganization = provider.getById(99);

        // Then there is no return value present
        assertFalse(optionalOrganization.isPresent());
    }

    @Test
    void shouldRetrieveADifferentOrganization() throws IOException, JsonParsingException {
        // Given 25 different organization
        InputStream stream = Resources.getResource("organizations.json").openStream();
        Organizations provider = new JsonOrganizations(stream);

        // When I select organization with ID 102
        Optional<Organization> optionalOrganization = provider.getById(102);

        // Then I find a organization
        assertTrue(optionalOrganization.isPresent());

        // And this organization has the correct name
        assertEquals("Nutralab", optionalOrganization.get().getName());
    }


    @Test
    void shouldThrowIfIdIsMissing() {
        String badJson = "[{\n" +
                "" +  // The `_id` field is removed from oneOrganizationJson
                "    \"url\": \"http://initech.zendesk.com/api/v2/organizations/101.json\",\n" +
                "    \"external_id\": \"9270ed79-35eb-4a38-a46f-35725197ea8d\",\n" +
                "    \"name\": \"Enthaze\",\n" +
                "    \"domain_names\": [\n" +
                "      \"kage.com\",\n" +
                "      \"ecratic.com\",\n" +
                "      \"endipin.com\",\n" +
                "      \"zentix.com\"\n" +
                "    ],\n" +
                "    \"created_at\": \"2016-05-21T11:10:28 -10:00\",\n" +
                "    \"details\": \"MegaCorp\",\n" +
                "    \"shared_tickets\": false,\n" +
                "    \"tags\": [\n" +
                "      \"Fulton\",\n" +
                "      \"West\",\n" +
                "      \"Rodriguez\",\n" +
                "      \"Farley\"\n" +
                "    ]\n" +
                "  }]";
        assertThrows(JsonParsingException.class, () -> new JsonOrganizations(badJson));
    }


    String oneOrganizationJson = "[{\n" +
            "    \"_id\": 101,\n" +
            "    \"url\": \"http://initech.zendesk.com/api/v2/organizations/101.json\",\n" +
            "    \"external_id\": \"9270ed79-35eb-4a38-a46f-35725197ea8d\",\n" +
            "    \"name\": \"Enthaze\",\n" +
            "    \"domain_names\": [\n" +
            "      \"kage.com\",\n" +
            "      \"ecratic.com\",\n" +
            "      \"endipin.com\",\n" +
            "      \"zentix.com\"\n" +
            "    ],\n" +
            "    \"created_at\": \"2016-05-21T11:10:28 -10:00\",\n" +
            "    \"details\": \"MegaCorp\",\n" +
            "    \"shared_tickets\": false,\n" +
            "    \"tags\": [\n" +
            "      \"Fulton\",\n" +
            "      \"West\",\n" +
            "      \"Rodriguez\",\n" +
            "      \"Farley\"\n" +
            "    ]\n" +
            "  }]";

}
