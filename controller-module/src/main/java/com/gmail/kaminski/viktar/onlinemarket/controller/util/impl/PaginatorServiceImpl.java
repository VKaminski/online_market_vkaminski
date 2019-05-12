package com.gmail.kaminski.viktar.onlinemarket.controller.util.impl;

import com.gmail.kaminski.viktar.onlinemarket.controller.model.Paginator;
import com.gmail.kaminski.viktar.onlinemarket.controller.util.PaginatorService;
import org.springframework.stereotype.Service;

@Service
public class PaginatorServiceImpl implements PaginatorService {
    @Override
    public Paginator get(Long page, Integer amountElement, Long sizeList) {
        Long amountPage = sizeList / amountElement;
        if (sizeList % amountElement != 0) {
            amountPage++;
        }
        Paginator paginator = new Paginator();
        paginator.setAmountElement(amountElement);
        paginator.setAmountPage(amountPage);
        paginator.setPage(page);
        return paginator;
    }
}
