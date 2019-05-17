package com.gmail.kaminski.viktar.onlinemarket.service;

import com.gmail.kaminski.viktar.onlinemarket.service.model.NewUserDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO getByEmail(String email);

    void add(NewUserDTO newUserDTO);

    List<UserDTO> getUsers(Long firstElement, Integer amountElement);

    Long getAmountUsers();

    void updateRole(Long id, String roleName);

    void delete(List<Long> checkedUsersId);

    void changePassword(Long id, int minLength, int maxLength);
}
