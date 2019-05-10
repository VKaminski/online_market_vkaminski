package com.gmail.kaminski.viktar.onlinemarket.repository;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserRepository extends GenericRepository {
    User getUserByEmail(Connection connection, String email);

    List<User> getUsers(Connection connection);

    void setRole(Connection connection, User user, String roleName);

    User add(Connection connection, User user);
}
