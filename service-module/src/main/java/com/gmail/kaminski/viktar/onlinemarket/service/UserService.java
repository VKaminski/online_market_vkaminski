package com.gmail.kaminski.viktar.onlinemarket.service;

import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    public UserDTO getUserByEmail(String email);

    void add(UserDTO userDTO);

    public List<UserDTO> getUsers();

    void setRole(UserDTO userDTO, String roleName);
}
