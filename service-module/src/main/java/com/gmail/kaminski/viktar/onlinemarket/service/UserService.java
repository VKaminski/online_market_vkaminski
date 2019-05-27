package com.gmail.kaminski.viktar.onlinemarket.service;

import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    UserDTO getByEmail(String email);

    @Transactional
    UserDTO getById(Long id);

    void add(UserDTO UserDTO);

    List<UserDTO> getUsers(Integer firstElement, Integer amountElement);

    Long getAmountUsers();

    void updateRole(Long userId, Long roleId);

    void delete(List<Long> checkedUsersId);

    void changePassword(Long id, int minLength, int maxLength);
}
