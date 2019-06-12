package com.gmail.kaminski.viktar.onlinemarket.service.converter;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.User;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserAuthorizedDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserNewDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;

public interface UserConverter {
    UserDTO toUserDTO(User user);

    User toUser(UserDTO userDTO);

    User toUser(UserNewDTO newUserDTO);

    UserAuthorizedDTO toAuthorizedUserDTO(User user);
}
