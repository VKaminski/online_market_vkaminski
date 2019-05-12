package com.gmail.kaminski.viktar.onlinemarket.repository;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserRepository extends GenericRepository {
    User get(Connection connection, String email);

    List<User> get(Connection connection, Long firstElement, Integer amountElement);

    void setRole(Connection connection, Long id, String roleName);

    User add(Connection connection, User user);

    Long size(Connection connection);

    void delete(Connection connection, List<Long> checkedUsersId);

    void changePassword(Connection connection, Long id, String hashPassword);

    User get(Connection connection, Long id);
}
