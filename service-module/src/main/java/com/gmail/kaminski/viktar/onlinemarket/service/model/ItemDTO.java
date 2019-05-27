package com.gmail.kaminski.viktar.onlinemarket.service.model;

import java.math.BigDecimal;

public class ItemDTO {
    private Long id;
    private String name;
    private String description;
    private String uniqNumber;
    private BigDecimal price;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setUniqNumber(String uniqNumber) {
        this.uniqNumber = uniqNumber;
    }

    public String getUniqNumber() {
        return uniqNumber;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
