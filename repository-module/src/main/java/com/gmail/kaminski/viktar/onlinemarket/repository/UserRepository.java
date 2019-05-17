package com.gmail.kaminski.viktar.onlinemarket.repository;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserRepository extends GenericRepository {
    User getById(Connection connection, Long id);

    User getByEmail(Connection connection, String email);

    List<User> getUsers(Connection connection, Long firstElement, Integer amountElement);

    Long getAmountUsers(Connection connection);

    void updateRole(Connection connection, Long id, String roleName);

    User add(Connection connection, User user);

    void delete(Connection connection, List<Long> checkedUsersId);

    void changePassword(Connection connection, Long id, String hashPassword);
}
