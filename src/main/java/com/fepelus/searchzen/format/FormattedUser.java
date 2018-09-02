package com.fepelus.searchzen.format;

import com.fepelus.searchzen.storage.User;

class FormattedUser {
    private final User user;
    private Storage storage;

    FormattedUser(User user, Storage storage) {
        this.user = user;
        this.storage = storage;
    }


    @Override
    public String toString() {
        return
            "_id: " + user.getId() + "\n" +
            "url: " + user.getUrl() + "\n" +
            "external_id: " + user.getExternalId() + "\n" +
            "name: " + user.getName() + "\n" +
            "alias: " + user.getAlias() + "\n" +
            "created_at: " + user.getCreatedAt() + "\n" +
            "active: " + user.getActive() + "\n" +
            "verified: " + user.getVerified() + "\n" +
            "shared: " + user.getShared() + "\n" +
            "locale: " + user.getLocale() + "\n" +
            "timezone: " + user.getTimezone() + "\n" +
            "last_login_at: " + user.getLastLoginAt() + "\n" +
            "email: " + user.getEmail() + "\n" +
            "phone: " + user.getPhone() + "\n" +
            "signature: " + user.getSignature() + "\n" +
            "organisation: " + storage.getOrganizationById(user.getOrganizationId()).map(org -> "\n  " + new FormattedOrganisation(org).compact()).orElse("") + "\n" +
            "suspended: " + user.getSuspended() + "\n" +
            "role: " + user.getRole() + "\n" +
            "tags: " + user.getTags() + "\n";
    }

    String compact() {
        return "_id:" + user.getId() +
                " url:" + user.getUrl() +
                " external_id:" + user.getExternalId() +
                " name:" + user.getName() +
                " alias:" + user.getAlias() +
                " created_at:" + user.getCreatedAt() +
                " active:" + user.getActive() +
                " verified:" + user.getVerified() +
                " shared:" + user.getShared() +
                " locale:" + user.getLocale() +
                " timezone:" + user.getTimezone() +
                " last_login_at:" + user.getLastLoginAt() +
                " email:" + user.getEmail() +
                " phone:" + user.getPhone() +
                " signature:" + user.getSignature() +
                " organisation:" + storage.getOrganizationById(user.getOrganizationId()).map(org ->  new FormattedOrganisation(org).compact()).orElse("") +
                " suspended:" + user.getSuspended() +
                " role:" + user.getRole() +
                " tags:" + user.getTags();
    }
}
