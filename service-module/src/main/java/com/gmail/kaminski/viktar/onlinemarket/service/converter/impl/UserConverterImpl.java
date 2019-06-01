package com.gmail.kaminski.viktar.onlinemarket.service.converter.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.Role;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.User;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.RoleConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.UserConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.AuthorizedUserDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.NewUserDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;
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
        if (user.getId() != null) {
            userDTO.setId(user.getId());
        }
        if (user.getName() != null) {
            userDTO.setName(user.getName());
        }
        if (user.getSurname() != null) {
            userDTO.setSurname(user.getSurname());
        }
        if (user.getPatronymic() != null) {
            userDTO.setPatronymic(user.getPatronymic());
        }
        if (user.getEmail() != null) {
            userDTO.setEmail(user.getEmail());
        }
        if (user.getPassword() != null) {
            userDTO.setPassword(user.getPassword());
        }
        if (user.getRole() != null) {
            userDTO.setRole(roleConverter.toRoleDTO(user.getRole()));
        }
        userDTO.setDeleted(user.isDeleted());
        return userDTO;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public User toUser(UserDTO userDTO) {
        User user = new User();
        if (userDTO.getId() != null) {
            user.setId(userDTO.getId());
        }
        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }
        if (userDTO.getSurname() != null) {
            user.setSurname(userDTO.getSurname());
        }
        if (userDTO.getPatronymic() != null) {
            user.setPatronymic(userDTO.getPatronymic());
        }
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getPassword() != null) {
            user.setPassword(userDTO.getPassword());
        }
        if (userDTO.getRole() != null) {
            user.setRole(roleConverter.toRole(userDTO.getRole()));
        }
        user.setDeleted(userDTO.isDeleted());
        return user;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public User toUser(NewUserDTO newUserDTO) {
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
        Role role = new Role();
        role.setName(newUserDTO.getRole());
        user.setRole(role);
        return user;
    }

    @Override
    public AuthorizedUserDTO toAuthorizedUserDTO(User user) {
        AuthorizedUserDTO authorizedUserDTO = new AuthorizedUserDTO();
        authorizedUserDTO.setId(user.getId());
        authorizedUserDTO.setEmail(user.getEmail());
        authorizedUserDTO.setName(user.getName());
        authorizedUserDTO.setSurname(user.getSurname());
        authorizedUserDTO.setPassword(user.getPassword());
        authorizedUserDTO.setRole(roleConverter.toRoleDTO(user.getRole()));
        return authorizedUserDTO;
    }
}
