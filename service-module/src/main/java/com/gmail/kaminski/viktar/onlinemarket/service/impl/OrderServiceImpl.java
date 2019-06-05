package com.gmail.kaminski.viktar.onlinemarket.service.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.ItemRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.OrderRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.UserRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Order;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.enums.OrderStatusEnum;
import com.gmail.kaminski.viktar.onlinemarket.service.OrderService;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.OrderConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.OrderDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.PageDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.validator.PageValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private OrderConverter orderConverter;
    private UserRepository userRepository;
    private ItemRepository itemRepository;
    private PageValidator pageValidator;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderConverter orderConverter,
                            UserRepository userRepository,
                            ItemRepository itemRepository,
                            PageValidator pageValidator) {
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.pageValidator = pageValidator;
    }

    @Override
    @Transactional
    public PageDTO<OrderDTO> getOrdersPage(PageDTO<OrderDTO> pageDTO, Long id) {
        pageDTO.setAmountElements(orderRepository.getAmountOfEntities());
        pageValidator.valid(pageDTO);
        int firstElement = (pageDTO.getPage() - 1) * pageDTO.getAmountElementsOnPage();
        int amountElementsOnPage = pageDTO.getAmountElementsOnPage();
        List<Order> orders;
        if (id == null) {
            orders = orderRepository.findAllOrdersSortedByDate(firstElement, amountElementsOnPage);
        } else {
            orders = orderRepository.findOrdersByUserIdSortedByDate(id, firstElement, amountElementsOnPage);
        }
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = orderConverter.toOrderDTO(order);
            BigDecimal totalPrice = orderDTO.getItem().getPrice().multiply(new BigDecimal(orderDTO.getAmount()));
            orderDTO.setTotalPrice(totalPrice);
            orderDTOs.add(orderDTO);
        }
        pageDTO.setElements(orderDTOs);
        return pageDTO;
    }

    @Override
    @Transactional
    public OrderDTO getById(Long id) {
        OrderDTO orderDTO = orderConverter.toOrderDTO(orderRepository.findById(id));
        BigDecimal totalPrice = orderDTO.getItem().getPrice().multiply(new BigDecimal(orderDTO.getAmount()));
        orderDTO.setTotalPrice(totalPrice);
        return orderDTO;
    }

    @Override
    @Transactional
    public void updateStatus(Long id, String newStatus) {
        Order order = orderRepository.findById(id);
        order.setStatus(OrderStatusEnum.valueOf(newStatus));
        orderRepository.update(order);
    }

    @Override
    @Transactional
    public void add(Long itemId, Integer amount, Long userId) {
        Order order = new Order();
        order.setCustomer(userRepository.findById(userId));
        order.setItem(itemRepository.findById(itemId));
        order.setAmount(amount);
        order.setUniqueNumber(UUID.randomUUID().toString());
        order.setStatus(OrderStatusEnum.valueOf("NEW"));
        order.setDate(new Date(System.currentTimeMillis()));
        orderRepository.add(order);
    }
}
