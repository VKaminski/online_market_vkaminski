package com.gmail.kaminski.viktar.onlinemarket.service.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.RoleRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.UserRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Profile;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Role;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.User;
import com.gmail.kaminski.viktar.onlinemarket.service.RandomService;
import com.gmail.kaminski.viktar.onlinemarket.service.UserService;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.UserConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.PageDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserAuthorizedDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserNewDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.validator.PageValidator;
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
    private PageValidator pageValidator;

    public UserServiceImpl(UserRepository userRepository,
                           UserConverter userConverter,
                           RandomService randomService,
                           RoleRepository roleRepository,
                           PageValidator pageValidator) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.randomService = randomService;
        this.roleRepository = roleRepository;
        this.pageValidator = pageValidator;
    }

    @Override
    @Transactional
    public UserAuthorizedDTO getByEmail(String email) {
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
    public boolean add(UserNewDTO newUserDTO) {
        if (userRepository.getByEmail(newUserDTO.getEmail()) == null) {
            User user = userConverter.toUser(newUserDTO);
            Profile profile = new Profile();
            profile.setUser(user);
            user.setProfile(profile);
            String newPassword = generatePassword(minPasswordLength, maxPasswordLength);
            String hashPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(cryptoRound));
            user.setPassword(hashPassword);
            userRepository.add(user);
            sendMessage(newPassword, user);
            return true;
        } else {
            return false;
        }
    }


    @Override
    @Transactional
    public PageDTO<UserDTO> getUsersPage(PageDTO<UserDTO> pageDTO) {
        pageDTO.setAmountElements(userRepository.getAmountOfEntities());
        pageValidator.valid(pageDTO);
        int amountElementsOnPage = pageDTO.getAmountElementsOnPage();
        int firstElement = (pageDTO.getPage() - 1) * pageDTO.getAmountElementsOnPage();
        List<User> users = userRepository.getAllUsersOrderByEmail(firstElement, amountElementsOnPage);
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
        String stringBuffer = "Hello " + user.getName() + " " + user.getSurname() +
                ", your new password " + newPassword;
        logger.debug(custom, stringBuffer);
    }

    private String generatePassword(int minLength, int maxLength) {
        int passwordLength = randomService.get(minLength, maxLength);
        return randomService.getLatinsAndNumbers(passwordLength);
    }


}
