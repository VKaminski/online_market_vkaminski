package com.gmail.kaminski.viktar.onlinemarket.service.converter;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.Item;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ItemDTO;

public interface ItemConverter {
    ItemDTO toItemDTO(Item item);

    Item toItem(ItemDTO itemDTO);
}
