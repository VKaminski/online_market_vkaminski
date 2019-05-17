package com.gmail.kaminski.viktar.onlinemarket.controller.util.impl;

import com.gmail.kaminski.viktar.onlinemarket.controller.model.Paginator;
import com.gmail.kaminski.viktar.onlinemarket.controller.util.PaginatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaginatorServiceImpl implements PaginatorService {
    private static final Logger logger = LoggerFactory.getLogger(PaginatorServiceImpl.class);

    @Override
    public Paginator get(String stringPage, String stringAmountElement, Long totalElement) {
        Long page = validPage(stringPage, totalElement);
        Integer amountElement = validAmountElement(stringAmountElement);
        Paginator paginator = new Paginator();
        paginator.setAmountElementOnPage(amountElement);
        paginator.setTotalElement(totalElement);
        paginator.setPage(page);
        return paginator;
    }

    private Long validPage(String stringPage, Long sizeList) {
        Long validPage = 1l;
        try {
            validPage = Long.parseLong(stringPage);
        } catch (NumberFormatException e) {
            logger.info("Not valid page");
        }
        if (validPage < 1 || validPage > sizeList){
            validPage = 1l;
        }
        return validPage;
    }

    private Integer validAmountElement(String stringAmountElement) {
        Integer validAmountElement = 1;
        try {
            validAmountElement = Integer.parseInt(stringAmountElement);
        } catch (NumberFormatException e) {
            logger.info("Not valid amountElement");
        }
        if (validAmountElement < 1 || validAmountElement > 15){
            validAmountElement = 10;
        }
        return validAmountElement;
    }
}
