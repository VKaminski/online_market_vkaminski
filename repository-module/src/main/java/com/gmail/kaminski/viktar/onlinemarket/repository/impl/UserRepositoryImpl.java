package com.gmail.kaminski.viktar.onlinemarket.repository.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.UserRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.exception.UserRepositoryException;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Role;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Override
    public User getUserByEmail(Connection connection, String email) {
        String sqlRequest = "SELECT U.password, R.name FROM User AS U" +
                " JOIN Role AS R ON U.role_id = R.id WHERE email=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                User user = new User();
                while (resultSet.next()) {
                    String password = resultSet.getString("password");
                    String roleName = resultSet.getString("name");
                    user.setEmail(email);
                    user.setPassword(password);
                    Role role = new Role();
                    role.setName(roleName);
                    user.setRole(role);
                }
                return user;
            } catch (SQLException e) {
                logger.error(this.getClass().getName() + " problem with ResultSet in getUserByEmail!");
                throw new UserRepositoryException(e);
            }
        } catch (SQLException e) {
            logger.error(this.getClass().getName() + " problem with prepareStatement in getUserByEmail!");
            throw new UserRepositoryException(e);
        }
    }

    @Override
    public List<User> getUsers(Connection connection, Long firstElement, Integer amountElement) {
        String sqlRequest = "SELECT U.id,U.surname,U.name,U.patronymic,U.email, U.deleted, R.name AS role" +
                " FROM User AS U JOIN Role AS R ON U.role_id = R.id" +
                " ORDER BY email" +
                " LIMIT ?,?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setLong(1, firstElement);
            preparedStatement.setInt(2, amountElement);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (resultSet.next()) {
                    users.add(get(resultSet));
                }
                return users;
            } catch (SQLException e) {
                logger.error(this.getClass().getName() + " problem with ResultSet in getUsers!");
                throw new UserRepositoryException(e);
            }
        } catch (SQLException e) {
            logger.error(this.getClass().getName() + " problem with prepareStatement in getUsers!");
            throw new UserRepositoryException(e);
        }
    }

    @Override
    public void setRole(Connection connection, Long id, String roleName) {
        String sqlRequest = "UPDATE User SET role_id = (SELECT (id) FROM Role WHERE name = ?)" +
                " WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setString(1, roleName);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(this.getClass().getName() + " problem with prepareStatement in setRole!");
            throw new UserRepositoryException(e);
        }

    }

    @Override
    public User add(Connection connection, User user) {
        String sqlRequest = "INSERT INTO User(surname, name, patronymic, email, password, role_id) " +
                "VALUES (?,?,?,?(SELECT (id) FROM Role WHERE name = ?))";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getSurname());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPatronymic());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getRole().getName());
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                resultSet.next();
                user.setId(resultSet.getLong(1));
                return user;
            } catch (SQLException e) {
                logger.error(this.getClass().getName() + " problem with ResultSet in add!");
                throw new UserRepositoryException(e);
            }
        } catch (SQLException e) {
            logger.error(this.getClass().getName() + " problem with PrepareStatement in add!");
            throw new UserRepositoryException(e);
        }
    }

    @Override
    public Long size(Connection connection) {
        String sqlRequest = "SELECT COUNT (*) FROM User WHERE deleted = false ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long size = resultSet.getLong(1);
                if (!resultSet.next()) {
                    return size;
                } else {
                    String message = this.getClass().getName() + " more than 1 result";
                    logger.error(message);
                    throw new UserRepositoryException(message);
                }
            } else {
                String message = this.getClass().getName() + " less than 1 result";
                logger.error(message);
                throw new UserRepositoryException(message);
            }
        } catch (SQLException e) {
            logger.error(this.getClass().getName() + " problem with PrepareStatement in size!");
            throw new UserRepositoryException(e);
        }
    }

    private User get(ResultSet resultSet) throws SQLException {
        User user = new User();
        try {
            Long id = resultSet.getLong("id");
            String surname = resultSet.getString("surname");
            String name = resultSet.getString("name");
            String patronymic = resultSet.getString("patronymic");
            String email = resultSet.getString("email");
            String roleName = resultSet.getString("role");
            Boolean deleted = resultSet.getBoolean("deleted");
            user.setId(id);
            user.setSurname(surname);
            user.setName(name);
            user.setPatronymic(patronymic);
            user.setEmail(email);
            Role role = new Role();
            role.setName(roleName);
            user.setRole(role);
            user.setDeleted(deleted);
            return user;
        } catch (SQLException e) {
            String errorMessage = "Exception: UserRepository private get";
            logger.error(errorMessage);
            throw new SQLException(errorMessage, e);
        }
    }
}
