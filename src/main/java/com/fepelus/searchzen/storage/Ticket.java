package com.fepelus.searchzen.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class Ticket {

    // Because we want to link entities together when outputting them
    private String id;
    private final long submitter_id;
    private final long assignee_id;
    private final long organization_id;

    // Give this structure a string and we know whether this entity matches it
    private Set<String> lookupValues = new HashSet<>();

    // Store the data in a hashmap rather than as fields on the object so that we can search by attribute name
    private Map<String, String> allStringAttributes = new HashMap<>();
    private Map<String, Set<String>> allSetAttributes = new HashMap<>();

    @JsonCreator
    Ticket(@JsonProperty("_id") String id, String url, String external_id, String created_at, String type,
                  String subject, String description, String priority, String status, long submitter_id,
                  long assignee_id, long organization_id, Set<String> tags, String has_incidents, String due_at,
                  String via) {
        this.id = id;
        this.submitter_id = submitter_id;
        this.assignee_id = assignee_id;
        this.organization_id = organization_id;

        lookupValues.addAll(Arrays.asList(id, url, external_id, created_at, type, subject, description, priority,
                status, "" + submitter_id, "" + assignee_id, "" + organization_id, has_incidents, due_at, via));
        lookupValues.addAll(tags);

        allStringAttributes.put("_id", id);
        allStringAttributes.put("url", url);
        allStringAttributes.put("external_id", external_id);
        allStringAttributes.put("created_at", created_at);
        allStringAttributes.put("type", type);
        allStringAttributes.put("subject", subject);
        allStringAttributes.put("description", description);
        allStringAttributes.put("priority", priority);
        allStringAttributes.put("status", status);
        allStringAttributes.put("submitter_id", "" + submitter_id);
        allStringAttributes.put("assignee_id", "" + assignee_id);
        allStringAttributes.put("organization_id", "" + organization_id);
        allStringAttributes.put("has_incidents", has_incidents);
        allStringAttributes.put("due_at", due_at);
        allStringAttributes.put("via", via);

        allSetAttributes.put("tags", tags);
    }

    boolean matches(SearchQuery query) {
        if (!lookupValues.contains(query.searchTerm())) {
            return false;
        }

        // there is at least one attribute that matches the search term

        if (query.searchAllAttributes()) {
            return true;
        }

        // but it may not match the attribute that we now know is specified

        return matchesByAttribute(query);
    }

    private boolean matchesByAttribute(SearchQuery query) {
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

    public String getId() {
        return id;
    }

    public String getUrl() {
        return allStringAttributes.get("url");
    }

    public String getExternalId() {
        return allStringAttributes.get("external_id");
    }

    public String getCreatedAt() {
        return allStringAttributes.get("created_at");
    }

    public String getType() {
        return allStringAttributes.get("type");
    }

    public String getSubject() {
        return allStringAttributes.get("subject");
    }

    public String getDescription() {
        return allStringAttributes.get("description");
    }

    public String getPriority() {
        return allStringAttributes.get("priority");
    }

    public String getStatus() {
        return allStringAttributes.get("status");
    }

    public long getSubmitterId() {
        return submitter_id;
    }

    public long getAssigneeId() {
        return assignee_id;
    }

    public long getOrganizationId() {
        return organization_id;
    }

    public String getHasIncidents() {
        return allStringAttributes.get("has_incidents");
    }

    public String getDueAt() {
        return allStringAttributes.get("due_at");
    }

    public String getVia() {
        return allStringAttributes.get("via");
    }

    public Set<String> getTags() {
        return allSetAttributes.get("tags");
    }

}
