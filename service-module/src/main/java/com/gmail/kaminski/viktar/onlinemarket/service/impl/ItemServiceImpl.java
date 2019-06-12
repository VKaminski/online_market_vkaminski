package com.gmail.kaminski.viktar.onlinemarket.service.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.ItemRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Item;
import com.gmail.kaminski.viktar.onlinemarket.service.ItemService;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.ItemConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ItemDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.PageDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.XMLItemDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.XMLItemsDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.parser.ItemJAXBParserService;
import com.gmail.kaminski.viktar.onlinemarket.service.validator.PageValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ItemServiceImpl implements ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");
    private ItemRepository itemRepository;
    private ItemConverter itemConverter;
    private PageValidator pageValidator;
    private ItemJAXBParserService parser;

    public ItemServiceImpl(ItemRepository itemRepository,
                           ItemConverter itemConverter,
                           PageValidator pageValidator,
                           ItemJAXBParserService parser) {
        this.itemRepository = itemRepository;
        this.itemConverter = itemConverter;
        this.pageValidator = pageValidator;
        this.parser = parser;
    }


    @Override
    @Transactional
    public Integer getAmountItems() {
        return itemRepository.getAmountOfEntities();
    }

    @Override
    @Transactional
    public PageDTO<ItemDTO> getItemsPage(PageDTO<ItemDTO> pageDTO) {
        pageDTO.setAmountElements(itemRepository.getAmountOfEntities());
        pageValidator.valid(pageDTO);
        int firstElement = (pageDTO.getPage() - 1) * pageDTO.getAmountElementsOnPage();
        int amountElementsOnPage = pageDTO.getAmountElementsOnPage();
        List<Item> items = itemRepository.findAllOrderByName(firstElement, amountElementsOnPage);
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

    @Override
    @Transactional
    public Map<String, Integer> addFromXML(MultipartFile file) {
        try {
            InputStream fileContent = file.getInputStream();
            XMLItemsDTO xmlItemsDTO = parser.unmarshall(fileContent);
            List<XMLItemDTO> itemsXML = xmlItemsDTO.getItemsDTO();
            int added = 0;
            int updated = 0;
            for (XMLItemDTO xmlItemDTO : itemsXML) {
                Item savedItem = itemRepository.findByUUID(xmlItemDTO.getUniqueNumber());
                Item xmlItem = itemConverter.toItem(xmlItemDTO);
                if (savedItem == null) {
                    itemRepository.add(xmlItem);
                    added++;
                } else {
                    itemRepository.update(itemConverter.update(savedItem, xmlItemDTO));
                    updated++;
                }
            }
            Map<String, Integer> result = new HashMap<>();
            result.put("added", added);
            result.put("updated", updated);
            return result;
        } catch (IOException e) {
            logger.debug(custom, e.getMessage());
        }
        return null;
    }
}
