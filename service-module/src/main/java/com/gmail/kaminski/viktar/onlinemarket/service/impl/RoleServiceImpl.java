package com.gmail.kaminski.viktar.onlinemarket.service.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.RoleRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.User;
import com.gmail.kaminski.viktar.onlinemarket.service.RoleService;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.RoleConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.exception.RoleServiceException;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<String> getRoleNames() {
        try (Connection connection = roleRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<String> roleNames = roleRepository.getRoleNames(connection);
                connection.commit();
                return roleNames;
            } catch (SQLException e) {
                logger.error(this.getClass().getName() + "rollback operation in getRoleNames");
                connection.rollback();
                throw new RoleServiceException(e);
            }
        } catch (SQLException e) {
            logger.error(this.getClass().getName() + "problem with connection in getRoleNames");
            throw new RoleServiceException(e);
        }
    }
}
