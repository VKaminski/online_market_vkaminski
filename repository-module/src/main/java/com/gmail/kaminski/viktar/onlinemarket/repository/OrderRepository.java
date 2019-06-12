package com.gmail.kaminski.viktar.onlinemarket.repository;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Order;

import java.util.List;

public interface OrderRepository extends GenericRepository<Long, Order> {
    List<Order> findAllOrdersSortedByDate(int firstElement, int amountElements);

    List<Order> findOrdersByUserIdSortedByDate(Long id, int firstElement, Integer amountElements);
}
