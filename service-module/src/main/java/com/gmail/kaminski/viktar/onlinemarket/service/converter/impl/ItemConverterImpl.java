package com.gmail.kaminski.viktar.onlinemarket.service.converter.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Item;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.ItemConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ItemDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.XMLItemDTO;
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
        itemDTO.setUniqueNumber(item.getUniqNumber());
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
        item.setUniqNumber(itemDTO.getUniqueNumber());
        item.setPrice(itemDTO.getPrice());
        return item;
    }

    @Override
    public Item toItem(XMLItemDTO xmlItemDTO) {
        Item item = new Item();
        item.setName(xmlItemDTO.getName());
        item.setDescription(xmlItemDTO.getDescription());
        item.setUniqNumber(xmlItemDTO.getUniqueNumber());
        item.setPrice(xmlItemDTO.getPrice());
        return item;
    }

    @Override
    public Item update(Item savedItem, XMLItemDTO xmlItemDTO) {
        savedItem.setName(xmlItemDTO.getName());
        savedItem.setDescription(xmlItemDTO.getDescription());
        savedItem.setPrice(xmlItemDTO.getPrice());
        return savedItem;
    }
}
