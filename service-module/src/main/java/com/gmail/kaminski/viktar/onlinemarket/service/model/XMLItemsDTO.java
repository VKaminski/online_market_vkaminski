package com.gmail.kaminski.viktar.onlinemarket.service.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "items")
public class XMLItemsDTO {
    @XmlElement(name = "item")
    private List<XMLItemDTO> itemsDTO;

    public List<XMLItemDTO> getItemsDTO() {
        return itemsDTO;
    }

    public void setItemsDTO(List<XMLItemDTO> itemsDTO) {
        this.itemsDTO = itemsDTO;
    }
}
