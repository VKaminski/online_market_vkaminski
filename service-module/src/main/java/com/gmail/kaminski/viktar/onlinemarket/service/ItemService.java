package com.gmail.kaminski.viktar.onlinemarket.service;

import com.gmail.kaminski.viktar.onlinemarket.service.model.ItemDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.PageDTO;
import org.springframework.transaction.annotation.Transactional;

public interface ItemService {

    Integer getAmountItems();

    PageDTO<ItemDTO> getItemsPage(PageDTO<ItemDTO> pageDTO);

    void delete(Long id);

    void copy(Long id);

    ItemDTO getById(Long id);

    void add(ItemDTO itemDTO);
}
