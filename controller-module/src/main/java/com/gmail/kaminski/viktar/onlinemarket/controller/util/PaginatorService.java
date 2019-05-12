package com.gmail.kaminski.viktar.onlinemarket.controller.util;

import com.gmail.kaminski.viktar.onlinemarket.controller.model.Paginator;

public interface PaginatorService {
    Paginator get(Long page, Integer amountElement, Long sizeList);
}
