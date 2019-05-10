package com.gmail.kaminski.viktar.onlinemarket.service.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.UserRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.exception.UserRepositoryException;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.User;
import com.gmail.kaminski.viktar.onlinemarket.service.UserService;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.UserConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.exception.UserServiceException;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRepository userRepository;
    private UserConverter userConverter;

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = userRepository.getUserByEmail(connection, email);
                UserDTO userDTO = userConverter.toUserDTO(user);
                connection.commit();
                return userDTO;
            } catch (UserRepositoryException e) {
                connection.rollback();
                logger.info("Operation getUserByEmail was rollback");
                throw new UserServiceException(e);
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
            throw new UserServiceException(e);
        }
    }

    @Override
    public void add(UserDTO userDTO) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            User user = userConverter.toUser(userDTO);
            try {
                String password = user.getPassword();
                String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
                user.setPassword(hashPassword);
                userRepository.add(connection, user);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(this.getClass().getName() + "rollback operation in add");
                throw new UserServiceException(e);
            }
        } catch (SQLException e) {
            logger.error(this.getClass().getName() + "problem with connection in add");
            throw new UserServiceException(e);
        }
    }


    @Override
    public List<UserDTO> getUsers() {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<User> users = userRepository.getUsers(connection);
                List<UserDTO> output = new ArrayList<>();
                for (User user : users) {
                    output.add(userConverter.toUserDTO(user));
                }
                connection.commit();
                return output;
            } catch (SQLException e) {
                logger.error(this.getClass().getName() + "rollback operation in getUsers");
                connection.rollback();
                throw new UserServiceException(e);
            }
        } catch (SQLException e) {
            logger.error(this.getClass().getName() + "problem with connection in getUsers");
            throw new UserServiceException(e);
        }
    }

    @Override
    public void setRole(UserDTO userDTO, String roleName) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = userConverter.toUser(userDTO);
                userRepository.setRole(connection, user, roleName);
                connection.commit();
            } catch (SQLException e) {
                logger.error(this.getClass().getName() + "rollback operation in setRole!");
                connection.rollback();
                throw new UserServiceException(e);
            }
        } catch (SQLException e) {
            logger.error(this.getClass().getName() + "problem with connection in setRole!");
            throw new UserServiceException(e);
        }
    }


}
