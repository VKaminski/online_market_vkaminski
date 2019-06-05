package com.gmail.kaminski.viktar.onlinemarket.controller.util.impl;

import com.gmail.kaminski.viktar.onlinemarket.controller.config.GlobalValue;
import com.gmail.kaminski.viktar.onlinemarket.controller.util.RequestParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RequestParamServiceImpl implements RequestParamService {
    private static final Logger logger = LoggerFactory.getLogger(RequestParamServiceImpl.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");
    private GlobalValue globalValue;

    public RequestParamServiceImpl(GlobalValue globalValue) {
        this.globalValue = globalValue;
    }

    @Override
    public int getPage(String stringPage, int maxPage, int defaultPage) {
        int validPage = Integer.getInteger(stringPage, defaultPage);
        if (validPage <= 0) {
            validPage = defaultPage;
        }
        return validPage;
    }

    @Override
    public int getInteger(String stringElement, int maxElement, int defaultElement) {
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
    public long getLong(String stringElement, long maxElement, long defaultElement) {
        long validAmountElement;
        try {
            validAmountElement = Long.parseLong(stringElement);
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
        SimpleDateFormat format = new SimpleDateFormat(globalValue.getDateFormat());
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

    @Override
    public void validXMLByScheme(MultipartFile file) {
        try {
            InputStream xmlStream = file.getInputStream();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream xsdStream = classLoader.getResourceAsStream(globalValue.getXsdScheme());
            Source xmlSource = new StreamSource(xmlStream);
            Source xsdSource = new StreamSource(xsdStream);
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(xsdSource);
            Validator validator = schema.newValidator();
            validator.validate(xmlSource);
        } catch (SAXException e) {
            logger.debug(custom, "XML not valid!");
        } catch (IOException e) {
            logger.debug(custom, "XML not valid!");
        }
    }

    @Override
    public Integer getAmountItems(String amount, Integer maxAmount) {
        Integer validAmount;
        try {
            validAmount = Integer.parseInt(amount);
            if (validAmount > maxAmount || validAmount <= 0){
                return null;
            }
            return validAmount;
        } catch (NumberFormatException e) {
            logger.debug(custom, "Not valid value: " + amount);
        }
        return null;
    }
}
