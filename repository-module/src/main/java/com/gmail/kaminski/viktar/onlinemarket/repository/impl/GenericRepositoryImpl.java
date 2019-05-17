package com.gmail.kaminski.viktar.onlinemarket.repository.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.GenericRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.exception.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Repository
public class GenericRepositoryImpl implements GenericRepository {

    private static Logger logger = LoggerFactory.getLogger(GenericRepositoryImpl.class);

    @Autowired
    private DataSource dataSource;

    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RepositoryException("Troubles with getting connection", e);
        }
    }
}
