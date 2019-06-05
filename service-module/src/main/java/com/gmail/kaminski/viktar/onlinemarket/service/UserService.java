package com.gmail.kaminski.viktar.onlinemarket.service;

import com.gmail.kaminski.viktar.onlinemarket.service.model.UserAuthorizedDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserNewDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.PageDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;

import java.util.List;

public interface UserService {
    UserAuthorizedDTO getByEmail(String email);

    UserDTO getById(Long id);

    boolean add(UserNewDTO newUserDTO);

    PageDTO<UserDTO> getUsersPage(PageDTO<UserDTO> pageDTO);

    Integer getAmountUsers();

    void updateRole(Long userId, Long roleId);

    void delete(List<Long> checkedUsersId);

    void changePassword(Long id, int minLength, int maxLength);
}
