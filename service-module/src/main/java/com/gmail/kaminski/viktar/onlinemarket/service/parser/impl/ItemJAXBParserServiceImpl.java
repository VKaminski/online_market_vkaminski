package com.gmail.kaminski.viktar.onlinemarket.service.parser.impl;

import com.gmail.kaminski.viktar.onlinemarket.service.model.XMLItemsDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.parser.ItemJAXBParserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

@Service
public class ItemJAXBParserServiceImpl implements ItemJAXBParserService {
    private static final Logger logger = LoggerFactory.getLogger(ItemJAXBParserServiceImpl.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");


    @Override
    public XMLItemsDTO unmarshall(InputStream fileContent) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(XMLItemsDTO.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (XMLItemsDTO) unmarshaller.unmarshal(fileContent);
        } catch (Exception e) {
            logger.debug(custom, "JAXB exception: " + e.getMessage());
            return null;
        }
    }
}
