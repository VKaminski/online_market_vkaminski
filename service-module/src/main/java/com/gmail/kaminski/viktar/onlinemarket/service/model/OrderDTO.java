package com.gmail.kaminski.viktar.onlinemarket.service.model;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.enums.OrderStatusEnum;

import java.math.BigDecimal;

public class OrderDTO {
    private Long id;
    private String uniqueNumber;
    private UserDTO customer;
    private ItemDTO item;
    private Integer amount;
    private BigDecimal totalPrice;
    private OrderStatusEnum status;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setCustomer(UserDTO customer) {
        this.customer = customer;
    }

    public UserDTO getCustomer() {
        return customer;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }

    public ItemDTO getItem() {
        return item;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
