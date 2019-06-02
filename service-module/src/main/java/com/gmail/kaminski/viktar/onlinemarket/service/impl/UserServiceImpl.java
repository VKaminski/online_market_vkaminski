package com.gmail.kaminski.viktar.onlinemarket.service.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.RoleRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.UserRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Role;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.User;
import com.gmail.kaminski.viktar.onlinemarket.service.RandomService;
import com.gmail.kaminski.viktar.onlinemarket.service.UserService;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.UserConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.AuthorizedUserDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.PageDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");
    @Value("${custom.password.crypto.round}")
    private int cryptoRound;
    @Value("${custom.password.min.length}")
    private int minPasswordLength;
    @Value("${custom.password.max.length}")
    private int maxPasswordLength;
    private UserRepository userRepository;
    private UserConverter userConverter;
    private RandomService randomService;
    private RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository,
                           UserConverter userConverter,
                           RandomService randomService,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.randomService = randomService;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public AuthorizedUserDTO getByEmail(String email) {
        User user = userRepository.getByEmail(email);
        return userConverter.toAuthorizedUserDTO(user);
    }

    @Override
    @Transactional
    public UserDTO getById(Long id) {
        User user = userRepository.findById(id);
        return userConverter.toUserDTO(user);
    }

    @Override
    @Transactional
    public void add(UserDTO UserDTO) {
        User user = userConverter.toUser(UserDTO);
        String newPassword = generatePassword(minPasswordLength, maxPasswordLength);
        String hashPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(cryptoRound));
        user.setPassword(hashPassword);
        userRepository.add(user);
        sendMessage(newPassword, user);
    }


    @Override
    @Transactional
    public PageDTO<UserDTO> getUsersPage(PageDTO<UserDTO> pageDTO) {
        int totalAmountElements = userRepository.getAmountOfEntities();
        Integer amountElements = pageDTO.getAmountElementsOnPage();
        pageDTO.setAmountElements(amountElements);
        int amountPages = totalAmountElements / amountElements + 1;
        if (pageDTO.getPage() > (amountPages)) {
            pageDTO.setPage(amountPages);
        }
        int firstElement = (pageDTO.getPage() - 1) * pageDTO.getAmountElementsOnPage();
        List<User> users = userRepository.getAllOrderByEmail(firstElement, amountElements);
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(userConverter.toUserDTO(user));
        }
        pageDTO.setElements(userDTOs);
        return pageDTO;
    }

    @Override
    @Transactional
    public Integer getAmountUsers() {
        return userRepository.getAmountOfEntities();
    }

    @Override
    @Transactional
    public void updateRole(Long userId, Long roleId) {
        User user = userRepository.findById(userId);
        if (!user.isLocked()) {
            Role role = roleRepository.findById(roleId);
            user.setRole(role);
            userRepository.update(user);
        }
    }

    @Override
    @Transactional
    public void delete(List<Long> checkedUsersId) {
        for (Long id : checkedUsersId) {
            User user = userRepository.findById(id);
            userRepository.remove(user);
        }
    }

    @Override
    @Transactional
    public void changePassword(Long id, int minLength, int maxLength) {
        String newPassword = generatePassword(minLength, maxLength);
        String hashPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(cryptoRound));
        User user = userRepository.findById(id);
        user.setPassword(hashPassword);
        userRepository.update(user);
        sendMessage(newPassword, user);
    }

    private void sendMessage(String newPassword, User user) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Hello ").append(user.getName()).append(" ").append(user.getSurname())
                .append(", your new password ").append(newPassword);
        logger.debug(custom, stringBuffer.toString());
    }

    private String generatePassword(int minLength, int maxLength) {
        int passwordLength = randomService.get(minLength, maxLength);
        return randomService.getLatinsAndNumbers(passwordLength);
    }


}
