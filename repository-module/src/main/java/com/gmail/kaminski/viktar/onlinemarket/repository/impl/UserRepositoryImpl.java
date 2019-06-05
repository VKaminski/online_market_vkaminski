package com.gmail.kaminski.viktar.onlinemarket.repository.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.UserRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<Long, User> implements UserRepository {

    @Override
    public User getByEmail(String email) {
        String hqlRequest = "select U from User U where U.email = :email and U.deleted = false";
        Query query = entityManager.createQuery(hqlRequest);
        query.setParameter("email", email);
        List<User> users = query.getResultList();
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<User> getAllUsersOrderByEmail(Integer firstElement, Integer amountElement) {
        String hqlRequest = "select U from User U where U.deleted = false order by U.email asc";
        Query query = entityManager.createQuery(hqlRequest)
                .setFirstResult(firstElement)
                .setMaxResults(amountElement);
        return query.getResultList();
    }
}
