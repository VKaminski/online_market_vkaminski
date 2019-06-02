package com.gmail.kaminski.viktar.onlinemarket.controller.util;

import java.util.Date;

public interface RequestParamService {
    int getPage(String stringPage, int maxPage, int defaultPage);

    int getElements(String stringElement, int maxElement, int defaultElement);

    Date getDate(String date, Long defaultTime);
}
