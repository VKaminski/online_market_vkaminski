package com.gmail.kaminski.viktar.onlinemarket.repository.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.ItemRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ItemRepositoryImpl extends GenericRepositoryImpl<Long, Item> implements ItemRepository {

    @Override
    public List<Item> getAllOrderByName(Integer firstElement, Integer amountElementsOnPage) {
        String hqlRequest = "from Item order by name asc";
        Query query = entityManager.createQuery(hqlRequest)
                .setFirstResult(firstElement)
                .setMaxResults(amountElementsOnPage);
        return query.getResultList();
    }
}
