package com.gmail.kaminski.viktar.onlinemarket.service.converter;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Order;
import com.gmail.kaminski.viktar.onlinemarket.service.model.OrderDTO;

public interface OrderConverter {
    OrderDTO toOrderDTO(Order order);

    Order toOrder(OrderDTO orderDTO);
}
