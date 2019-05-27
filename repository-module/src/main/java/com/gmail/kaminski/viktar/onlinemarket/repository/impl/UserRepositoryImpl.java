package com.gmail.kaminski.viktar.onlinemarket.repository.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.UserRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<Long, User> implements UserRepository {

    @Override
    public User getByEmail(String email) {
        String hqlRequest = "select U from User U where U.email = :email";
        Query query = entityManager.createQuery(hqlRequest);
        query.setParameter("email", email);
        return (User) query.getSingleResult();
    }
}
