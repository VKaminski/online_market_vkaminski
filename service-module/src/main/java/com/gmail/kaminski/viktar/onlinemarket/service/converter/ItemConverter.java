package com.gmail.kaminski.viktar.onlinemarket.service.converter;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Item;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ItemDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.XMLItemDTO;

public interface ItemConverter {
    ItemDTO toItemDTO(Item item);

    Item toItem(ItemDTO itemDTO);

    Item toItem(XMLItemDTO xmlItemDTO);

    Item update(Item savedItem, XMLItemDTO xmlItemDTO);
}
