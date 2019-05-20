package com.gmail.kaminski.viktar.onlinemarket.repository;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.Profile;

import java.sql.Connection;

public interface ProfileRepository extends GenericRepository {
    Profile getByUserEmail(Connection connection, String email);
}
