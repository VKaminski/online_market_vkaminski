package com.gmail.kaminski.viktar.onlinemarket.repository;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.User;

import java.util.List;

public interface UserRepository extends GenericRepository<Long, User> {

    List<User> findAll(int firstElement, int amountElement);

    User getByEmail(String email);

    List<User> getAllOrderByEmail(Integer firstElement, Integer amountElement);
}
