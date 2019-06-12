package com.gmail.kaminski.viktar.onlinemarket.service.converter.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Order;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.ItemConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.OrderConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.UserConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.OrderDTO;
import org.springframework.stereotype.Component;

@Component
public class OrderConverterImpl implements OrderConverter {
    private UserConverter userConverter;
    private ItemConverter itemConverter;

    public OrderConverterImpl(UserConverter userConverter,
                              ItemConverter itemConverter) {
        this.userConverter = userConverter;
        this.itemConverter = itemConverter;
    }

    @Override
    public OrderDTO toOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setUniqueNumber(order.getUniqueNumber());
        orderDTO.setCustomer(userConverter.toUserDTO(order.getCustomer()));
        orderDTO.setItem(itemConverter.toItemDTO(order.getItem()));
        orderDTO.setAmount(order.getAmount());
        orderDTO.setStatus(order.getStatus());
        return orderDTO;
    }

    @Override
    public Order toOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setItem(itemConverter.toItem(orderDTO.getItem()));
        order.setCustomer(userConverter.toUser(orderDTO.getCustomer()));
        order.setStatus(orderDTO.getStatus());
        order.setAmount(orderDTO.getAmount());
        return order;
    }
}
