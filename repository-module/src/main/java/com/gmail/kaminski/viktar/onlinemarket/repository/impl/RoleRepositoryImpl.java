package com.gmail.kaminski.viktar.onlinemarket.repository.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.RoleRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.exception.RoleRepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleRepositoryImpl extends GenericRepositoryImpl implements RoleRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Override
    public List<String> getRoleNames(Connection connection) {
        String sqlRequest = "SELECT (name) FROM Role";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<String> roles = new ArrayList<>();
                while (resultSet.next()) {
                    roles.add(resultSet.getString("name"));
                }
                return roles;
            } catch (SQLException e) {
                logger.error(this.getClass().getName() + " problem with ResultSet in getRoleNames!");
                throw new RoleRepositoryException(e);
            }
        } catch (SQLException e) {
            logger.error(this.getClass().getName() + " problem with prepareStatement in getRoleNames!");
            throw new RoleRepositoryException(e);
        }
    }

}
