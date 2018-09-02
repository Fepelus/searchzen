package com.fepelus.searchzen.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fepelus.searchzen.contracts.SearchQuery;
import org.apache.commons.collections.OrderedMap;

import java.util.*;
import java.util.stream.Collectors;

/** The object parsed from JSON
 *
 */
public class Organization {
    // Because we want to link entities together when outputting them
    private long id;

    // Give this structure a string and we know whether this entity matches it
    private Set<String> lookupValues = new HashSet<>();

    // Store the data in a hashmap rather than as fields on the object so that we can search by attribute name
    private Map<String, String> allStringAttributes = new LinkedHashMap<>();
    private Map<String, Set<String>> allSetAttributes = new HashMap<>();


    @JsonCreator()
    public Organization(@JsonProperty(value = "_id", required = true) long id, String url, String external_id,
                        String name, Set<String> domain_names, String created_at, String details,
                        String shared_tickets, Set<String> tags) {
        this.id = id;

        lookupValues.addAll(Arrays.asList("" + id, url, external_id, name, created_at, details, shared_tickets));
        lookupValues.addAll(domain_names);
        lookupValues.addAll(tags);

        allStringAttributes.put("_id", "" + id);
        allStringAttributes.put("url", url);
        allStringAttributes.put("external_id", external_id);
        allStringAttributes.put("name", name);
        allStringAttributes.put("created_at", created_at);
        allStringAttributes.put("details", details);
        allStringAttributes.put("shared_tickets", shared_tickets);

        allSetAttributes.put("domain_names", domain_names);
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

    public String toString() {
        return allStringAttributes.keySet().stream().map(key -> key + ": " + allStringAttributes.get(key)).collect(Collectors.joining("\n")) + "\n" +
                allSetAttributes.keySet().stream().map(key -> key + ": " + allSetAttributes.get(key).toString()).collect(Collectors.joining("\n"));
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
    public String getCreatedAt() {
        return allStringAttributes.get("created_at");
    }
    public String getDetails() {
        return allStringAttributes.get("details");
    }
    public String getSharedTickets() {
        return allStringAttributes.get("shared_tickets");
    }
    public Set<String> getDomainNames() {
        return allSetAttributes.get("domain_names");
    }
    public Set<String> getTags() {
        return allSetAttributes.get("tags");
    }
}
