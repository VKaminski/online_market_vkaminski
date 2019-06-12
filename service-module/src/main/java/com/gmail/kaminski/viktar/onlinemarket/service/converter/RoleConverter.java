package com.gmail.kaminski.viktar.onlinemarket.service.converter;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Role;
import com.gmail.kaminski.viktar.onlinemarket.service.model.RoleDTO;

public interface RoleConverter {
    Role toRole(RoleDTO roleDTO);

    RoleDTO toRoleDTO(Role role);
}
