package com.gmail.kaminski.viktar.onlinemarket.service;

import com.gmail.kaminski.viktar.onlinemarket.service.model.ItemDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.PageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ItemService {

    Integer getAmountItems();

    PageDTO<ItemDTO> getItemsPage(PageDTO<ItemDTO> pageDTO);

    void delete(Long id);

    void copy(Long id);

    ItemDTO getById(Long id);

    void add(ItemDTO itemDTO);

    Map<String, Integer> addFromXML(MultipartFile file);
}
