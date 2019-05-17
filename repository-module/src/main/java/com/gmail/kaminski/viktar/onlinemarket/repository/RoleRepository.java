package com.gmail.kaminski.viktar.onlinemarket.repository;

import java.sql.Connection;
import java.util.List;

public interface RoleRepository extends GenericRepository {
    List<String> getRoleNames(Connection connection);
}
