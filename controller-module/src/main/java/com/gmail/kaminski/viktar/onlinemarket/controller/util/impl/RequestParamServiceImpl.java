package com.gmail.kaminski.viktar.onlinemarket.controller.util.impl;

import com.gmail.kaminski.viktar.onlinemarket.controller.util.RequestParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RequestParamServiceImpl implements RequestParamService {
    private static final Logger logger = LoggerFactory.getLogger(RequestParamServiceImpl.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");
    @Value("${custom.date.format}")
    private String dateFormat;

    @Override
    public int getPage(String stringPage, int maxPage, int defaultPage) {
        int validPage = Integer.getInteger(stringPage, defaultPage);
        if (validPage <= 0) {
            validPage = defaultPage;
        }
        return validPage;
    }

    @Override
    public int getElements(String stringElement, int maxElement, int defaultElement) {
        int validAmountElement;
        try {
            validAmountElement = Integer.parseInt(stringElement);
            if (validAmountElement <= 0) {
                logger.debug(custom, "Not valid value: " + stringElement + ". Set default value: " + defaultElement);
                validAmountElement = defaultElement;
            } else if (validAmountElement > maxElement) {
                logger.debug(custom, "Not valid value: " + stringElement + ". Set default value: " + defaultElement);
                validAmountElement = maxElement;
            }
        } catch (NumberFormatException e) {
            logger.debug(custom, "Not valid value: " + stringElement + ". Set default value: " + defaultElement);
            validAmountElement = defaultElement;
        }
        return validAmountElement;
    }

    @Override
    public Date getDate(String date, Long defaultTime) {
        Date output;
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        if (date == null) {
            return new Date(defaultTime);
        }
        try {
            output = format.parse(date);
        } catch (ParseException e) {
            logger.debug(custom, "Not valid value: " + date + ". Set default value: " + defaultTime);
            output = new Date(defaultTime);
        }
        return output;
    }
}
