package com.fepelus.searchzen.contracts;

import com.fepelus.searchzen.storage.Organization;
import com.fepelus.searchzen.storage.User;

import java.util.Optional;

public interface Storage {
    Optional<Organization> getOrganizationById(long organizationId);

    Optional<User> getUserById(long userId);
}
