package com.gmail.kaminski.viktar.onlinemarket.service.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.UserRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.exception.UserRepositoryException;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.User;
import com.gmail.kaminski.viktar.onlinemarket.service.EmailService;
import com.gmail.kaminski.viktar.onlinemarket.service.RandomService;
import com.gmail.kaminski.viktar.onlinemarket.service.UserService;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.UserConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.exception.UserServiceException;
import com.gmail.kaminski.viktar.onlinemarket.service.model.NewUserDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Value("${custom.password.crypto.round}")
    private int cryptoRound;
    @Value("${custom.password.min.length}")
    private int minPasswordLength;
    @Value("${custom.password.max.length}")
    private int maxPasswordLength;
    private UserRepository userRepository;
    private UserConverter userConverter;
    private EmailService emailService;
    private RandomService randomService;

    private UserServiceImpl(UserRepository userRepository,
                            UserConverter userConverter,
                            EmailService emailService,
                            RandomService randomService) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.emailService = emailService;
        this.randomService = randomService;
    }

    @Override
    public UserDTO getByEmail(String email) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = userRepository.getByEmail(connection, email);
                UserDTO userDTO = userConverter.toUserDTO(user);
                connection.commit();
                return userDTO;
            } catch (UserRepositoryException e) {
                connection.rollback();
                logger.info("Operation getByEmail (by email) was rollback");
                throw new UserServiceException(e);
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
            throw new UserServiceException(e);
        }
    }

    @Override
    public void add(NewUserDTO newUserDTO) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            User user = userConverter.toUser(newUserDTO);
            try {
                String newPassword = generatePassword(minPasswordLength, maxPasswordLength);
                String hashPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(cryptoRound));
                user.setPassword(hashPassword);
                userRepository.add(connection, user);
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Hello ").append(user.getName()).append(" ").append(user.getSurname())
                        .append(", your new password ").append(newPassword);
                emailService.sendEmail(user.getEmail(), "New Password", stringBuffer.toString());
                connection.commit();
            } catch (UserRepositoryException e) {
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
    public List<UserDTO> getUsers(Long firstElement, Integer amountElement) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<User> users = userRepository.getUsers(connection, firstElement, amountElement);
                List<UserDTO> output = new ArrayList<>();
                for (User user : users) {
                    output.add(userConverter.toUserDTO(user));
                }
                connection.commit();
                return output;
            } catch (UserRepositoryException e) {
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
    public Long getAmountUsers() {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Long size = userRepository.getAmountUsers(connection);
                connection.commit();
                return size;
            } catch (UserRepositoryException e) {
                logger.error(this.getClass().getName() + "rollback operation in getAmountUsers!");
                connection.rollback();
                throw new UserServiceException(e);
            }
        } catch (SQLException e) {
            logger.error(this.getClass().getName() + "problem with connection in getAmountUsers!");
            throw new UserServiceException(e);
        }
    }

    @Override
    public void updateRole(Long id, String roleName) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                userRepository.updateRole(connection, id, roleName);
                connection.commit();
            } catch (UserRepositoryException e) {
                logger.error(this.getClass().getName() + "rollback operation in updateRole!");
                connection.rollback();
                throw new UserServiceException(e);
            }
        } catch (SQLException e) {
            logger.error(this.getClass().getName() + "problem with connection in updateRole!");
            throw new UserServiceException(e);
        }
    }

    @Override
    public void delete(List<Long> checkedUsersId) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                userRepository.delete(connection, checkedUsersId);
                connection.commit();
            } catch (UserRepositoryException e) {
                logger.error(this.getClass().getName() + "rollback operation in delete!");
                connection.rollback();
                throw new UserServiceException(e);
            }
        } catch (SQLException e) {
            logger.error(this.getClass().getName() + "problem with connection in delete!");
            throw new UserServiceException(e);
        }
    }

    @Override
    public void changePassword(Long id, int minLength, int maxLength) {
        String newPassword = generatePassword(minLength, maxLength);
        String hashPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(cryptoRound));
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                userRepository.changePassword(connection, id, hashPassword);
                User user = userRepository.getById(connection, id);
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Hello ").append(user.getName()).append(" ").append(user.getSurname())
                        .append(", your new password ").append(newPassword);
                emailService.sendEmail(user.getEmail(), "New Password", stringBuffer.toString());
                connection.commit();
            } catch (UserRepositoryException e) {
                logger.error(this.getClass().getName() + "rollback operation in changePassword!");
                connection.rollback();
                throw new UserServiceException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private String generatePassword(int minLength, int maxLength) {
        int passwordLength = randomService.get(minLength, maxLength);
        String newPassword = randomService.getLatinsAndNumbers(passwordLength);
        return newPassword;
    }


}
