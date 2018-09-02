package com.fepelus.searchzen.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fepelus.searchzen.contracts.SearchQuery;

import java.util.*;

public class User {

    // Because we want to link entities together when outputting them
    private final long id;
    private final long organization_id;

    // Give this structure a string and we know whether this entity matches it
    private Set<String> lookupValues = new HashSet<>();

    // Store the data in a hashmap rather than as fields on the object so that we can search by attribute name
    private Map<String, String> allStringAttributes = new HashMap<>();
    private Map<String, Set<String>> allSetAttributes = new HashMap<>();

    @JsonCreator()
    public User(@JsonProperty(value = "_id", required = true) long id, String url, String external_id, String name,
                String alias, String created_at, String active, String verified, String shared, String locale,
                String timezone, String last_login_at, String email, String phone, String signature,
                long organization_id, Set<String> tags, String suspended, String role) {


        this.id = id;
        this.organization_id = organization_id;

        this.lookupValues.addAll(Arrays.asList("" + id, url, external_id, name, alias, created_at, active, verified,
                shared, locale, timezone, last_login_at, email, phone, signature, "" + organization_id, suspended, role));
        this.lookupValues.addAll(tags);

        allStringAttributes.put("_id", "" + id);
        allStringAttributes.put("url", url);
        allStringAttributes.put("external_id", external_id);
        allStringAttributes.put("name", name);
        allStringAttributes.put("alias", alias);
        allStringAttributes.put("created_at", created_at);
        allStringAttributes.put("active", active);
        allStringAttributes.put("verified", verified);
        allStringAttributes.put("shared", shared);
        allStringAttributes.put("locale", locale);
        allStringAttributes.put("timezone", timezone);
        allStringAttributes.put("last_login_at", last_login_at);
        allStringAttributes.put("email", email);
        allStringAttributes.put("phone", phone);
        allStringAttributes.put("signature", signature);
        allStringAttributes.put("organization_id", "" + organization_id);
        allStringAttributes.put("suspended", suspended);
        allStringAttributes.put("role", role);

        allSetAttributes.put("tags", tags);
    }


    public boolean matches(SearchQuery query) {
        if (!lookupValues.contains(query.searchTerm())) {
            return false;
        }

        // there is at least one attribute that matches the search term

        if (query.searchAllAttributes()) {
            return true;
        }

        // but it may not match the attribute that we now know is specified

        return query.limitToAttributes().stream()
                .anyMatch(attribute -> {
                    String stringValue = allStringAttributes.get(attribute);
                    if (query.searchTerm().equals(stringValue)) {
                        return true;
                    }

                    Set<String> setValues = allSetAttributes.get(attribute);
                    return (setValues != null && setValues.contains(query.searchTerm()));
                });
    }

    public long getId() {
        return id;
    }

    public String getUrl() {
        return allStringAttributes.get("url");
    }

    public String getExternalId() {
        return allStringAttributes.get("external_id");
    }

    public String getName() {
        return allStringAttributes.get("name");
    }

    public String getAlias() {
        return allStringAttributes.get("alias");
    }

    public String getCreatedAt() {
        return allStringAttributes.get("created_at");
    }

    public String getActive() {
        return allStringAttributes.get("active");
    }

    public String getVerified() {
        return allStringAttributes.get("verified");
    }

    public String getShared() {
        return allStringAttributes.get("shared");
    }

    public String getLocale() {
        return allStringAttributes.get("locale");
    }

    public String getTimezone() {
        return allStringAttributes.get("timezone");
    }

    public String getLastLoginAt() {
        return allStringAttributes.get("last_login_at");
    }

    public String getEmail() {
        return allStringAttributes.get("email");
    }

    public String getPhone() {
        return allStringAttributes.get("phone");
    }

    public String getSignature() {
        return allStringAttributes.get("signature");
    }

    public long getOrganizationId() {
        return organization_id;
    }

    public String getSuspended() {
        return allStringAttributes.get("suspended");
    }

    public String getRole() {
        return allStringAttributes.get("role");
    }

    public Set<String> getTags() {
        return allSetAttributes.get("tags");
    }

}
