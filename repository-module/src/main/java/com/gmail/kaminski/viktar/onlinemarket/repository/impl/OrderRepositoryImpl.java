package com.gmail.kaminski.viktar.onlinemarket.repository.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.OrderRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class OrderRepositoryImpl extends GenericRepositoryImpl<Long, Order> implements OrderRepository {
    @Override
    public List<Order> findAllOrdersSortedByDate(int firstElement, int amountElements) {
        String hqlRequest = "from Order O order by O.date desc";
        Query query = entityManager.createQuery(hqlRequest)
                .setFirstResult(firstElement)
                .setMaxResults(amountElements);
        return query.getResultList();
    }

    @Override
    public List<Order> findOrdersByUserIdSortedByDate(Long id, int firstElement, Integer amountElements) {
        String hqlRequest = "from Order O where O.customer.id = :id and O.deleted = false order by O.date desc";
        Query query = entityManager.createQuery(hqlRequest)
                .setFirstResult(firstElement)
                .setMaxResults(amountElements);
        query.setParameter("id", id);
        return query.getResultList();
    }
}
