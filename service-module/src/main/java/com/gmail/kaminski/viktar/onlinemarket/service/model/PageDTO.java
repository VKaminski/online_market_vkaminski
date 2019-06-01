package com.gmail.kaminski.viktar.onlinemarket.service.model;

import java.util.ArrayList;
import java.util.List;

public class PageDTO<T> {
    private Integer page;
    private Integer amountElementsOnPage;
    private Integer amountElements;
    private List<T> elements = new ArrayList<>();

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getAmountElementsOnPage() {
        return amountElementsOnPage;
    }

    public void setAmountElementsOnPage(Integer amountElementsOnPage) {
        this.amountElementsOnPage = amountElementsOnPage;
    }

    public Integer getAmountElements() {
        return amountElements;
    }

    public void setAmountElements(Integer amountElements) {
        this.amountElements = amountElements;
    }

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }
}
