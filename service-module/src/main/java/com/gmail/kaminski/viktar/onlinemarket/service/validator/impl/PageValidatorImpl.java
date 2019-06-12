package com.gmail.kaminski.viktar.onlinemarket.service.validator.impl;

import com.gmail.kaminski.viktar.onlinemarket.service.model.PageDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.validator.PageValidator;
import org.springframework.stereotype.Component;

@Component
public class PageValidatorImpl implements PageValidator {
    @Override
    public PageDTO valid(PageDTO pageDTO) {
        int amountElements = pageDTO.getAmountElements();
        pageDTO.setAmountElements(amountElements);
        Integer amountElementsOnPage = pageDTO.getAmountElementsOnPage();
        int amountPages = amountElements / amountElementsOnPage + 1;
        if (pageDTO.getPage() > (amountPages)) {
            pageDTO.setPage(amountPages);
        }
        return null;
    }
}
