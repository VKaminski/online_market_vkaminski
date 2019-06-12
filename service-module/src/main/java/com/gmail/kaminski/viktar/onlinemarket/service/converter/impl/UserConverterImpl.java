package com.gmail.kaminski.viktar.onlinemarket.service.converter.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.User;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.RoleConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.UserConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserAuthorizedDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserNewDTO;
import org.springframework.stereotype.Component;

@Component
public class UserConverterImpl implements UserConverter {
    private RoleConverter roleConverter;

    public UserConverterImpl(RoleConverter roleConverter) {
        this.roleConverter = roleConverter;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(roleConverter.toRoleDTO(user.getRole()));
        userDTO.setDeleted(user.isDeleted());
        return userDTO;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public User toUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setDeleted(userDTO.isDeleted());
        return user;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public User toUser(UserNewDTO newUserDTO) {
        User user = new User();
        if (newUserDTO.getName() != null) {
            user.setName(newUserDTO.getName());
        }
        if (newUserDTO.getSurname() != null) {
            user.setSurname(newUserDTO.getSurname());
        }
        if (newUserDTO.getPatronymic() != null) {
            user.setPatronymic(newUserDTO.getPatronymic());
        }
        if (newUserDTO.getEmail() != null) {
            user.setEmail(newUserDTO.getEmail());
        }
        user.setRole(roleConverter.toRole(newUserDTO.getRole()));
        return user;
    }

    @Override
    public UserAuthorizedDTO toAuthorizedUserDTO(User user) {
        UserAuthorizedDTO authorizedUserDTO = new UserAuthorizedDTO();
        authorizedUserDTO.setId(user.getId());
        authorizedUserDTO.setEmail(user.getEmail());
        authorizedUserDTO.setName(user.getName());
        authorizedUserDTO.setSurname(user.getSurname());
        authorizedUserDTO.setPassword(user.getPassword());
        authorizedUserDTO.setRole(roleConverter.toRoleDTO(user.getRole()));
        return authorizedUserDTO;
    }
}
