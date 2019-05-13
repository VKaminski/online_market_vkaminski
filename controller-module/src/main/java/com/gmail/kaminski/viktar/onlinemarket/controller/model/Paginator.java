package com.gmail.kaminski.viktar.onlinemarket.controller.model;

import java.util.ArrayList;
import java.util.List;

public class Paginator {
    private Long page;
    private Integer amountElement;
    private Long amountPage;
    private List<Integer> amountVariants;

    public Paginator() {
        amountVariants = new ArrayList<>();
        amountVariants.add(5);
        amountVariants.add(10);
        amountVariants.add(15);
    }

    public Paginator(Long page, Integer amountElement, Long amountPage) {
        this.page = page;
        this.amountElement = amountElement;
        this.amountPage = amountPage;
        amountVariants = new ArrayList<>();
        amountVariants.add(5);
        amountVariants.add(10);
        amountVariants.add(15);
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Integer getAmountElement() {
        return amountElement;
    }

    public void setAmountElement(Integer amountElement) {
        this.amountElement = amountElement;
    }

    public Long getAmountPage() {
        return amountPage;
    }

    public void setAmountPage(Long amountPage) {
        this.amountPage = amountPage;
    }

    public List<Integer> getAmountVariants() {
        return amountVariants;
    }
}
