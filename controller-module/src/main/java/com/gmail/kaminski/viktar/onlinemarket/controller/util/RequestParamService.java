package com.gmail.kaminski.viktar.onlinemarket.controller.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public interface RequestParamService {
    int getPage(String stringPage, int maxPage, int defaultPage);

    int getInteger(String stringElement, int maxElement, int defaultElement);

    long getLong(String stringElement, long maxElement, long defaultElement);

    Date getDate(String date, Long defaultTime);

    void validXMLByScheme(MultipartFile file);

    Integer getAmountItems(String amount, Integer maxAmount);
}
