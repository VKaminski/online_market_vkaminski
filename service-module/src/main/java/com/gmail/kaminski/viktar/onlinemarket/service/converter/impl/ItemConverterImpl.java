package com.gmail.kaminski.viktar.onlinemarket.service.converter.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.Item;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.ItemConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ItemDTO;
import org.springframework.stereotype.Component;

@Component
public class ItemConverterImpl implements ItemConverter {
    @Override
    public ItemDTO toItemDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        if (item.getId() != null) {
            itemDTO.setId(item.getId());
        }
        itemDTO.setName(item.getName());
        itemDTO.setDescription(item.getDescription());
        itemDTO.setUniqNumber(item.getUniqNumber());
        itemDTO.setPrice(item.getPrice());
        return itemDTO;
    }

    @Override
    public Item toItem(ItemDTO itemDTO) {
        Item item = new Item();
        if (itemDTO.getId() != null) {
            item.setId(itemDTO.getId());
        }
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setUniqNumber(itemDTO.getUniqNumber());
        item.setPrice(itemDTO.getPrice());
        return item;



    }
}
