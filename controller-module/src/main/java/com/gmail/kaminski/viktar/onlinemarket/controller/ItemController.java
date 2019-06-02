package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.controller.util.RequestParamService;
import com.gmail.kaminski.viktar.onlinemarket.service.ItemService;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ItemDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.PageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ItemController {
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");
    private ItemService itemService;
    private RequestParamService requestParamService;

    public ItemController(ItemService itemService,
                          RequestParamService requestParamService) {
        this.itemService = itemService;
        this.requestParamService = requestParamService;
    }

    @GetMapping("/items")
    public String showItems(
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "amountElement", required = false, defaultValue = "10") String amountElement,
            Model model) {
        logger.debug(custom, "page: " + page + " amountElement: " + amountElement);
        PageDTO<ItemDTO> itemsPage = new PageDTO<>();
        itemsPage.setPage(requestParamService.getElements(page, Integer.MAX_VALUE, 1));
        itemsPage.setAmountElementsOnPage(requestParamService.getElements(amountElement, 40, 10));
        itemService.getItemsPage(itemsPage);
        model.addAttribute("itemsPage", itemsPage);
        return "items";
    }

    @GetMapping("/items/{id}")
    public String showArticle(
            @PathVariable("id") Long id,
            Model model) {
        ItemDTO itemDTO = itemService.getById(id);
        model.addAttribute("item", itemDTO);
        return "item";
    }

    @PostMapping("/items/{id}/delete")
    public String deleteItem(
            @PathVariable("id") Long id) {
        itemService.delete(id);
        return "redirect:/items";
    }

    @PostMapping("/items/{id}/copy")
    public String copyItem(
            @PathVariable("id") Long id) {
        itemService.copy(id);
        return "redirect:/items";
    }
}
