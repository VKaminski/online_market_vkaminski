package com.gmail.kaminski.viktar.onlinemarket.service.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.ItemRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Item;
import com.gmail.kaminski.viktar.onlinemarket.service.ItemService;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.ItemConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ItemDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.PageDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;
    private ItemConverter itemConverter;

    public ItemServiceImpl(ItemRepository itemRepository, ItemConverter itemConverter) {
        this.itemRepository = itemRepository;
        this.itemConverter = itemConverter;
    }



    @Override
    @Transactional
    public Integer getAmountItems() {
        return itemRepository.getAmountOfEntities();
    }

    @Override
    @Transactional
    public PageDTO<ItemDTO> getItemsPage(PageDTO<ItemDTO> pageDTO) {
        int amountElements = itemRepository.getAmountOfEntities();
        pageDTO.setAmountElements(amountElements);
        Integer amountElementsOnPage = pageDTO.getAmountElementsOnPage();
        int amountPages = amountElements / amountElementsOnPage + 1;
        if (pageDTO.getPage() > (amountPages)) {
            pageDTO.setPage(amountPages);
        }
        int firstElement = (pageDTO.getPage() - 1) * pageDTO.getAmountElementsOnPage();
        List<Item> items = itemRepository.getAllOrderByName(firstElement, amountElementsOnPage);
        List<ItemDTO> itemDTOs = new ArrayList<>();
        for (Item item : items) {
            itemDTOs.add(itemConverter.toItemDTO(item));
        }
        pageDTO.setElements(itemDTOs);
        return pageDTO;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        itemRepository.remove(itemRepository.findById(id));
    }

    @Override
    @Transactional
    public void copy(Long id) {
        Item item = itemRepository.findById(id);
        Item newItem = new Item();
        newItem.setName("Copy " + item.getName());
        newItem.setDescription(item.getDescription());
        newItem.setPrice(item.getPrice());
        newItem.setUniqNumber(UUID.randomUUID().toString());
        itemRepository.add(newItem);
    }

    @Override
    @Transactional
    public ItemDTO getById(Long id) {
        return itemConverter.toItemDTO(itemRepository.findById(id));
    }

    @Override
    @Transactional
    public void add(ItemDTO itemDTO) {
        itemRepository.add(itemConverter.toItem(itemDTO));
    }
}
