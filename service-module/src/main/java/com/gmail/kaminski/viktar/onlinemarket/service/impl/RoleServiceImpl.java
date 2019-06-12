package com.gmail.kaminski.viktar.onlinemarket.service.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.RoleRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Role;
import com.gmail.kaminski.viktar.onlinemarket.service.RoleService;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.RoleConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.RoleDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;
    private RoleConverter roleConverter;

    public RoleServiceImpl(RoleRepository roleRepository, RoleConverter roleConverter) {
        this.roleRepository = roleRepository;
        this.roleConverter = roleConverter;
    }

    @Override
    @Transactional
    public List<RoleDTO> getAll() {
        List<RoleDTO> output = new ArrayList<>();
        List<Role> roles = roleRepository.findAll(0, Integer.MAX_VALUE);
        for (Role role : roles) {
            output.add(roleConverter.toRoleDTO(role));
        }
        return output;
    }
}
