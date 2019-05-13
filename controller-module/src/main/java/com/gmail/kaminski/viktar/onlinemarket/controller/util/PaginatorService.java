package com.gmail.kaminski.viktar.onlinemarket.controller.util;

import com.gmail.kaminski.viktar.onlinemarket.controller.model.Paginator;

public interface PaginatorService {
    Paginator get(String stringPage, String stringAmountElement, Long sizeList);
}
