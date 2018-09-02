package com.fepelus.searchzen.storage;

import com.fepelus.searchzen.contracts.Users;
import com.google.common.io.Resources;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TestUsers {
    @Test
    void shouldProvideUsers() throws JsonParsingException {
        Users provider = new JsonUsers("[]");
        assertEquals(0, provider.getAll().size());
    }
    @Test
    void shouldParseOneUser() throws JsonParsingException {
        Users provider = new JsonUsers(oneUserJson);
        assertEquals(1, provider.getAll().size());
    }

    @Test
    void shouldAcceptInputStream() throws JsonParsingException {
        Users provider = new JsonUsers(new ByteArrayInputStream(oneUserJson.getBytes(StandardCharsets.UTF_8)));
        assertEquals(1, provider.getAll().size());
    }

    @Test
    void shouldAcceptStreamOfResourceFile() throws IOException, JsonParsingException {
        // Given a stream of data from the test resources directory
        InputStream stream = Resources.getResource("users.json").openStream();

        // When the json is parsed in the constructor
        Users provider = new JsonUsers(stream);

        // Then the number of users parsed from the stream matches expectation
        assertEquals(75, provider.getAll().size());
    }


    @Test
    void shouldRetrieveUserById() throws JsonParsingException {
        // Given a list of one user
        Users provider = new JsonUsers(oneUserJson);

        // When I get the user by her ID
        Optional<User> optionalUser = provider.getById(1);

        // Then I find a user
        assertTrue(optionalUser.isPresent());

        // And the user has the correct name
        assertEquals("Francisca Rasmussen", optionalUser.get().getName());
    }

    @Test
    void shouldNotRetrieveIfIdIsNotPresent() throws JsonParsingException {
        // Given a list of one user
        Users provider = new JsonUsers(oneUserJson);

        // When I query by an ID that is not in the data
        Optional<User> optionalUser = provider.getById(100);

        // Then I do not find a user
        assertFalse(optionalUser.isPresent());
    }


    @Test
    void shouldRetrieveADifferentUser() throws IOException, JsonParsingException {
        // Given 75 different users
        InputStream stream = Resources.getResource("users.json").openStream();
        Users provider = new JsonUsers(stream);

        // When I select user with ID 2
        Optional<User> user = provider.getById(2);

        // Then I find a user
        assertTrue(user.isPresent());

        // And this user has the correct name
        assertEquals("Cross Barlow", user.get().getName());
    }

    @Test
    void shouldThrowIfCannotParseJson() {
        assertThrows(JsonParsingException.class, () -> new JsonUsers("[{bunch: 'of nonsense'}]"));
    }

    @Test
    void shouldThrowIfIdIsMissing() {
        assertThrows(JsonParsingException.class, () -> new JsonUsers("[\n" +
                "  {\n" +
                "" +   // _id is missing
                "    \"url\": \"http://initech.zendesk.com/api/v2/users/1.json\",\n" +
                "    \"external_id\": \"74341f74-9c79-49d5-9611-87ef9b6eb75f\",\n" +
                "    \"name\": \"Francisca Rasmussen\",\n" +
                "    \"alias\": \"Miss Coffey\",\n" +
                "    \"created_at\": \"2016-04-15T05:19:46 -10:00\",\n" +
                "    \"active\": true,\n" +
                "    \"verified\": true,\n" +
                "    \"shared\": false,\n" +
                "    \"locale\": \"en-AU\",\n" +
                "    \"timezone\": \"Sri Lanka\",\n" +
                "    \"last_login_at\": \"2013-08-04T01:03:27 -10:00\",\n" +
                "    \"email\": \"coffeyrasmussen@flotonic.com\",\n" +
                "    \"phone\": \"8335-422-718\",\n" +
                "    \"signature\": \"Don't Worry Be Happy!\",\n" +
                "    \"organization_id\": 119,\n" +
                "    \"tags\": [\n" +
                "      \"Diaperville\"\n" +
                "    ],\n" +
                "    \"suspended\": true,\n" +
                "    \"role\": \"admin\"\n" +
                "  }]"));
    }



    String oneUserJson = "[\n" +
            "  {\n" +
            "    \"_id\": 1,\n" +
            "    \"url\": \"http://initech.zendesk.com/api/v2/users/1.json\",\n" +
            "    \"external_id\": \"74341f74-9c79-49d5-9611-87ef9b6eb75f\",\n" +
            "    \"name\": \"Francisca Rasmussen\",\n" +
            "    \"alias\": \"Miss Coffey\",\n" +
            "    \"created_at\": \"2016-04-15T05:19:46 -10:00\",\n" +
            "    \"active\": true,\n" +
            "    \"verified\": true,\n" +
            "    \"shared\": false,\n" +
            "    \"locale\": \"en-AU\",\n" +
            "    \"timezone\": \"Sri Lanka\",\n" +
            "    \"last_login_at\": \"2013-08-04T01:03:27 -10:00\",\n" +
            "    \"email\": \"coffeyrasmussen@flotonic.com\",\n" +
            "    \"phone\": \"8335-422-718\",\n" +
            "    \"signature\": \"Don't Worry Be Happy!\",\n" +
            "    \"organization_id\": 119,\n" +
            "    \"tags\": [\n" +
            "      \"Springville\",\n" +
            "      \"Sutton\",\n" +
            "      \"Hartsville/Hartley\",\n" +
            "      \"Diaperville\"\n" +
            "    ],\n" +
            "    \"suspended\": true,\n" +
            "    \"role\": \"admin\"\n" +
            "  }]";

}
