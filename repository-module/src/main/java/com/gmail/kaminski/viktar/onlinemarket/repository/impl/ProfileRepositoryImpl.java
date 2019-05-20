package com.gmail.kaminski.viktar.onlinemarket.repository.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.ProfileRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.exception.ProfileRepositoryException;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Profile;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ProfileRepositoryImpl extends GenericRepositoryImpl implements ProfileRepository {
    private static final Logger logger = LoggerFactory.getLogger(ArticleRepositoryImpl.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");

    @Override
    public Profile getByUserEmail(Connection connection, String email) {
        String sqlRequest = "SELECT " +
                "U.id AS userId, U.surname AS userSurname, U.name AS userName" +
                ", P.address AS profileAddress, P.phone AS profilePhone " +
                "FROM Profile AS P " +
                "JOIN User AS U ON U.id = P.id " +
                "WHERE U.email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Profile profile = new Profile();
                while (resultSet.next()) {
                    Long id = resultSet.getLong("userId");
                    String surname = resultSet.getString("userSurname");
                    String name = resultSet.getString("userName");
                    User user = new User();
                    user.setId(id);
                    user.setSurname(surname);
                    user.setName(name);
                    profile.setUser(user);
                    String address = resultSet.getString("profileAddress");
                    String phone = resultSet.getString("profilePhone");
                    profile.setAddress(address);
                    profile.setPhone(phone);
                }
                return profile;
            } catch (SQLException e) {
                logger.debug(custom, this.getClass().getName() + " problem with ResultSet in getById!");
                throw new ProfileRepositoryException(e);
            }
        } catch (SQLException e) {
            logger.debug(custom, this.getClass().getName() + " problem with prepareStatement in getById!");
            throw new ProfileRepositoryException(e);
        }
    }
}
