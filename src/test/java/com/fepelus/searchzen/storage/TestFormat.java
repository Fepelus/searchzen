package com.fepelus.searchzen.storage;

import com.fepelus.searchzen.contracts.Storage;
import com.fepelus.searchzen.format.Format;
import com.fepelus.searchzen.search.SearchResults;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestFormat {

    public static final User USER = new User(75, "http://initech.zendesk.com/api/v2/users/75.json",
            "0db0c1da-8901-4dc3-a469-fe4b500d0fca", "Catalina Simpson", "Miss Rosanna",
            "2016-06-07T09:18:00 -10:00", "false", "true", "true",
            "zh-CN", "US Minor Outlying Islands", "2012-10-15T12:36:41 -11:00",
            "rosannasimpson@flotonic.com", "8615-883-099", "Don't Worry Be Happy!",
            119, Sets.newHashSet("Veguita", "Navarre", "Elizaville","Beaulieu"),
            "true", "agent");

    public static final String FORMATTED_USER ="_id: 75\n" +
            "url: http://initech.zendesk.com/api/v2/users/75.json\n" +
            "external_id: 0db0c1da-8901-4dc3-a469-fe4b500d0fca\n" +
            "name: Catalina Simpson\n" +
            "alias: Miss Rosanna\n" +
            "created_at: 2016-06-07T09:18:00 -10:00\n" +
            "active: false\n" +
            "verified: true\n" +
            "shared: true\n" +
            "locale: zh-CN\n" +
            "timezone: US Minor Outlying Islands\n" +
            "last_login_at: 2012-10-15T12:36:41 -11:00\n" +
            "email: rosannasimpson@flotonic.com\n" +
            "phone: 8615-883-099\n" +
            "signature: Don't Worry Be Happy!\n" +
            "organisation: \n" +
            "  _id:125 url:http://initech.zendesk.com/api/v2/organizations/125.json external_id:42a1a845-70cf-40ed-a762-acb27fd606cc name:Strezzö created_at:2016-02-21T06:11:51 -11:00 details:MegaCorpshared_tickets:falsedomain_names:[corpulse.com, teraprene.com, techtrix.com, flotonic.com] tags:[Jacobs, Ray, Vance, Frank]\n" +
            "suspended: true\n" +
            "role: agent\n" +
            "tags: [Beaulieu, Veguita, Elizaville, Navarre]";

    public static final Ticket TICKET = new Ticket("436bf9b0-1147-4c0a-8439-6f79833bff5b",
            "http://initech.zendesk.com/api/v2/tickets/436bf9b0-1147-4c0a-8439-6f79833bff5b.json",
            "9210cdc9-4bee-485f-a078-35396cd74063", "2016-04-28T11:19:34 -10:00",
            "incident", "A Catastrophe in Korea (North)",
            "Nostrud ad sit velit cupidatat laboris ipsum nisi amet laboris ex exercitation amet et proident. Ipsum fugiat aute dolore tempor nostrud velit ipsum.",
            "high", "pending", 38, 24, 125,
            Sets.newHashSet("Ohio", "Pennsylvania", "American Samoa", "Northern Mariana Islands"),
            "false", "2016-07-31T02:37:50 -10:00", "web");


    public static final String FORMATTED_TICKET =
            "_id: 436bf9b0-1147-4c0a-8439-6f79833bff5b\n" +
            "url: http://initech.zendesk.com/api/v2/tickets/436bf9b0-1147-4c0a-8439-6f79833bff5b.json\n" +
            "external_id: 9210cdc9-4bee-485f-a078-35396cd74063\n" +
            "created_at: 2016-04-28T11:19:34 -10:00\n" +
            "type: incident\n" +
            "subject: A Catastrophe in Korea (North)\n" +
            "description: Nostrud ad sit velit cupidatat laboris ipsum nisi amet laboris ex exercitation amet et proident. Ipsum fugiat aute dolore tempor nostrud velit ipsum.\n" +
            "priority: high\n" +
            "status: pending\n" +
            "submitter: \n" +
            "  _id:75 url:http://initech.zendesk.com/api/v2/users/75.json external_id:0db0c1da-8901-4dc3-a469-fe4b500d0fca name:Catalina Simpson alias:Miss Rosanna created_at:2016-06-07T09:18:00 -10:00 active:false verified:true shared:true locale:zh-CN timezone:US Minor Outlying Islands last_login_at:2012-10-15T12:36:41 -11:00 email:rosannasimpson@flotonic.com phone:8615-883-099 signature:Don't Worry Be Happy! organisation:_id:125 url:http://initech.zendesk.com/api/v2/organizations/125.json external_id:42a1a845-70cf-40ed-a762-acb27fd606cc name:Strezzö created_at:2016-02-21T06:11:51 -11:00 details:MegaCorpshared_tickets:falsedomain_names:[corpulse.com, teraprene.com, techtrix.com, flotonic.com] tags:[Jacobs, Ray, Vance, Frank] suspended:true role:agent tags:[Beaulieu, Veguita, Elizaville, Navarre]\n" +
            "assignee: \n" +
            "  _id:75 url:http://initech.zendesk.com/api/v2/users/75.json external_id:0db0c1da-8901-4dc3-a469-fe4b500d0fca name:Catalina Simpson alias:Miss Rosanna created_at:2016-06-07T09:18:00 -10:00 active:false verified:true shared:true locale:zh-CN timezone:US Minor Outlying Islands last_login_at:2012-10-15T12:36:41 -11:00 email:rosannasimpson@flotonic.com phone:8615-883-099 signature:Don't Worry Be Happy! organisation:_id:125 url:http://initech.zendesk.com/api/v2/organizations/125.json external_id:42a1a845-70cf-40ed-a762-acb27fd606cc name:Strezzö created_at:2016-02-21T06:11:51 -11:00 details:MegaCorpshared_tickets:falsedomain_names:[corpulse.com, teraprene.com, techtrix.com, flotonic.com] tags:[Jacobs, Ray, Vance, Frank] suspended:true role:agent tags:[Beaulieu, Veguita, Elizaville, Navarre]\n" +
            "organization: \n" +
            "  _id:125 url:http://initech.zendesk.com/api/v2/organizations/125.json external_id:42a1a845-70cf-40ed-a762-acb27fd606cc name:Strezzö created_at:2016-02-21T06:11:51 -11:00 details:MegaCorpshared_tickets:falsedomain_names:[corpulse.com, teraprene.com, techtrix.com, flotonic.com] tags:[Jacobs, Ray, Vance, Frank]\n" +
            "has_incidents: false\n" +
            "due_at: 2016-07-31T02:37:50 -10:00\n" +
            "via: web\n" +
            "tags: [American Samoa, Northern Mariana Islands, Ohio, Pennsylvania]";


    public static final Organization ORGANIZATION = new Organization(125,
            "http://initech.zendesk.com/api/v2/organizations/125.json", "42a1a845-70cf-40ed-a762-acb27fd606cc",
            "Strezzö", Sets.newHashSet("techtrix.com", "teraprene.com", "corpulse.com", "flotonic.com"),
            "2016-02-21T06:11:51 -11:00", "MegaCorp", "false",
            Sets.newHashSet("Vance", "Ray", "Jacobs", "Frank"));

    public static final String FORMATTED_ORGANIZATION = "_id: 125\n" +
            "url: http://initech.zendesk.com/api/v2/organizations/125.json\n" +
            "external_id: 42a1a845-70cf-40ed-a762-acb27fd606cc\n" +
            "name: Strezzö\n" +
            "created_at: 2016-02-21T06:11:51 -11:00\n" +
            "details: MegaCorp\n" +
            "shared_tickets: false\n" +
            "domain_names: [corpulse.com, teraprene.com, techtrix.com, flotonic.com]\n" +
            "tags: [Jacobs, Ray, Vance, Frank]";

    @Test
    void shouldDisplaySearchResultForOrganization() {
        SearchResults results = new SearchResults();
        results.matchingOrganizations(Arrays.asList(ORGANIZATION));
        results.matchingTickets(Arrays.asList());
        results.matchingUsers(Arrays.asList());
        Storage storage = new FakeStorage();
        Format format = new Format(storage);
        assertEquals(FORMATTED_ORGANIZATION, format.format(results));
    }


    @Test
    void shouldDisplaySearchResultForUser() {
        SearchResults results = new SearchResults();
        results.matchingOrganizations(Arrays.asList());
        results.matchingTickets(Arrays.asList());
        results.matchingUsers(Arrays.asList(USER));
        Storage storage = new FakeStorage();
        Format format = new Format(storage);
        assertEquals(FORMATTED_USER, format.format(results));
    }


    @Test
    void shouldDisplaySearchResultForTicket() {
        SearchResults results = new SearchResults();
        results.matchingOrganizations(Arrays.asList());
        results.matchingTickets(Arrays.asList(TICKET));
        results.matchingUsers(Arrays.asList());
        Storage storage = new FakeStorage();
        Format format = new Format(storage);
        assertEquals(FORMATTED_TICKET, format.format(results));
    }

    @Test
    void shouldDisplaySearchResultForTicketAndOrganisation() {
        SearchResults results = new SearchResults();
        results.matchingOrganizations(Arrays.asList(ORGANIZATION));
        results.matchingTickets(Arrays.asList(TICKET, TICKET));
        results.matchingUsers(Arrays.asList(USER));
        Storage storage = new FakeStorage();
        Format format = new Format(storage);
        assertEquals(FORMATTED_ORGANIZATION + "\n\n" + FORMATTED_USER + "\n\n" + FORMATTED_TICKET + "\n\n" + FORMATTED_TICKET, format.format(results));
    }



    class FakeStorage implements Storage {

    @Override
    public Optional<Organization> getOrganizationById(long organizationId) {
        return Optional.of(ORGANIZATION);
    }

    @Override
    public Optional<User> getUserById(long userId) {
        return Optional.of(USER);
    }
}

}
