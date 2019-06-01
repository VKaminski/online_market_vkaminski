package com.gmail.kaminski.viktar.onlinemarket.repository.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.UserRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<Long, User> implements UserRepository {

    @Override
    public User getByEmail(String email) {
        String hqlRequest = "select U from User U where U.email = :email";
        Query query = entityManager.createQuery(hqlRequest);
        query.setParameter("email", email);
        return (User) query.getSingleResult();
    }

    @Override
    public List<User> getAllOrderByEmail(Integer firstElement, Integer amountElement) {
        String hqlRequest = "select U from User U order by U.email asc";
        Query query = entityManager.createQuery(hqlRequest)
                .setFirstResult(firstElement)
                .setMaxResults(amountElement);
        return query.getResultList();
    }
}
