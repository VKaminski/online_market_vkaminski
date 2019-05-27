package com.gmail.kaminski.viktar.onlinemarket.controller.model;

import java.util.ArrayList;
import java.util.List;

public class Paginator {
    private Integer page;
    private Integer amountElementOnPage;
    private Long totalElement;
    private List<Integer> amountVariants;

    public Paginator() {
        amountVariants = new ArrayList<>();
        amountVariants.add(5);
        amountVariants.add(10);
        amountVariants.add(15);
    }

    public Paginator(Integer page, Integer amountElementOnPage, Long totalElement) {
        this.page = page;
        this.amountElementOnPage = amountElementOnPage;
        this.totalElement = totalElement;
        amountVariants = new ArrayList<>();
        amountVariants.add(5);
        amountVariants.add(10);
        amountVariants.add(15);
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getAmountElementOnPage() {
        return amountElementOnPage;
    }

    public void setAmountElementOnPage(Integer amountElementOnPage) {
        this.amountElementOnPage = amountElementOnPage;
    }

    public Long getTotalElement() {
        return totalElement;
    }

    public void setTotalElement(Long totalElement) {
        this.totalElement = totalElement;
    }

    public List<Integer> getAmountVariants() {
        return amountVariants;
    }
}
