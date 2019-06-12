package com.gmail.kaminski.viktar.onlinemarket.service.parser;

import com.gmail.kaminski.viktar.onlinemarket.service.model.XMLItemsDTO;

import java.io.InputStream;

public interface ItemJAXBParserService {
    XMLItemsDTO unmarshall(InputStream fileContent);
}
