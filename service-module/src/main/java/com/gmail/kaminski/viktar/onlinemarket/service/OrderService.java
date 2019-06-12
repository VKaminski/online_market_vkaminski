package com.gmail.kaminski.viktar.onlinemarket.service;

import com.gmail.kaminski.viktar.onlinemarket.service.model.OrderDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.PageDTO;

public interface OrderService {
    PageDTO<OrderDTO> getOrdersPage(PageDTO<OrderDTO> pageDTO, Long id);

    OrderDTO getById(Long id);

    void updateStatus(Long id, String newStatus);

    void add(Long itemId, Integer amount, Long userId);
}
