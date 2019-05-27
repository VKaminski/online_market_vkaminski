package com.gmail.kaminski.viktar.onlinemarket.service;

import com.gmail.kaminski.viktar.onlinemarket.service.model.ItemDTO;

import java.util.List;

public interface ItemService {
    Long getAmountItems();

    List<ItemDTO> getItems(Integer firstElement, Integer amountElement);

    void delete(Long id);

    void copy(Long id);

    ItemDTO getById(Long id);

    void add(ItemDTO itemDTO);
}
