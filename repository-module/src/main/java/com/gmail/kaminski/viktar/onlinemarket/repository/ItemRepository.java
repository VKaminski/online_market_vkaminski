package com.gmail.kaminski.viktar.onlinemarket.repository;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Item;

import java.util.List;

public interface ItemRepository extends GenericRepository<Long, Item> {
    List<Item> findAllOrderByName(Integer firstElement, Integer amountElementsOnPage);

    Item findByUUID(String uniqueNumber);
}
