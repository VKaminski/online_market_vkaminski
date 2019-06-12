package com.gmail.kaminski.viktar.onlinemarket.service.converter.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Role;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.RoleConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.RoleDTO;
import org.springframework.stereotype.Component;

@Component
public class RoleConverterImpl implements RoleConverter {

    @Override
    public Role toRole(RoleDTO roleDTO) {
        Role role = new Role();
        if (roleDTO.getId() != null) {
            role.setId(roleDTO.getId());
        }
        role.setName(roleDTO.getName());
        return role;
    }

    @Override
    public RoleDTO toRoleDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        if (role.getId() != null) {
            roleDTO.setId(role.getId());
        }
        roleDTO.setName(role.getName());
        return roleDTO;
    }
}
