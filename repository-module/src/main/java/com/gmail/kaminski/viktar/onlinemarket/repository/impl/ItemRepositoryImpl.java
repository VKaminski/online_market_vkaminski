package com.gmail.kaminski.viktar.onlinemarket.repository.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.ItemRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ItemRepositoryImpl extends GenericRepositoryImpl<Long, Item> implements ItemRepository {

    @Override
    public List<Item> findAllOrderByName(Integer firstElement, Integer amountElementsOnPage) {
        String hqlRequest = "from Item I where I.deleted = false  order by name asc";
        Query query = entityManager.createQuery(hqlRequest)
                .setFirstResult(firstElement)
                .setMaxResults(amountElementsOnPage);
        return query.getResultList();
    }

    @Override
    public Item findByUUID(String uniqueNumber) {
        String hqlRequest = "select I from Item I where I.uniqNumber = :uniqNumber and I.deleted = false";
        Query query = entityManager.createQuery(hqlRequest);
        query.setParameter("uniqNumber", uniqueNumber);
        List<Item> items = query.getResultList();
        if (items.size() > 0) {
            return items.get(0);
        } else {
            return null;
        }
    }
}
