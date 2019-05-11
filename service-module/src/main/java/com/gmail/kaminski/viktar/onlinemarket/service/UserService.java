package com.gmail.kaminski.viktar.onlinemarket.service;

import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO getUserByEmail(String email);

    void add(UserDTO userDTO);

    List<UserDTO> getUsers(Long firstElement, Integer amountElement);

    Long amountPages(Integer pageSize);

    void setRole(Long id, String roleName);
}
