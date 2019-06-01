package com.gmail.kaminski.viktar.onlinemarket.repository;

import java.util.List;

public interface GenericRepository<I, T> {

    T findById(I id);

    void add(T entity);

    void update(T entity);

    void remove(T entity);

    List<T> findAll(int firstElement, int amountElement);

    Integer getAmountOfEntities();
}
