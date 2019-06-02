package com.gmail.kaminski.viktar.onlinemarket.service.converter;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.User;
import com.gmail.kaminski.viktar.onlinemarket.service.model.AuthorizedUserDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.NewUserDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;

public interface UserConverter {
    UserDTO toUserDTO(User user);

    User toUser(UserDTO userDTO);

    User toUser(NewUserDTO newUserDTO);

    AuthorizedUserDTO toAuthorizedUserDTO(User user);
}
