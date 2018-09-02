package com.fepelus.searchzen.format;


import com.fepelus.searchzen.storage.Organization;

class FormattedOrganisation {
    private Organization org;

    FormattedOrganisation(Organization org) {
        this.org = org;
    }

    @Override
    public String toString() {
        return
        "_id: " + org.getId() + "\n" +
        "url: " + org.getUrl() + "\n" +
        "external_id: " + org.getExternalId() + "\n" +
        "name: " + org.getName() + "\n" +
        "created_at: " + org.getCreatedAt() + "\n" +
        "details: " + org.getDetails() + "\n" +
        "shared_tickets: " + org.getSharedTickets() + "\n" +
        "domain_names: " + org.getDomainNames() + "\n" +
        "tags: " + org.getTags() + "\n";
    }

    String compact() {
        return "_id:"  + org.getId() + " url:" + org.getUrl() + " external_id:" + org.getExternalId() +
                " name:" + org.getName() + " created_at:" + org.getCreatedAt() + " details:" + org.getDetails() +
                 "shared_tickets:" + org.getSharedTickets() + "domain_names:" + org.getDomainNames() +
                " tags:" + org.getTags();

    }
}
