package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.controller.model.Paginator;
import com.gmail.kaminski.viktar.onlinemarket.controller.util.PaginatorService;
import com.gmail.kaminski.viktar.onlinemarket.service.ItemService;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ItemDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ItemController {

    private PaginatorService paginatorService;
    private ItemService itemService;

    public ItemController(PaginatorService paginatorService, ItemService itemService) {
        this.paginatorService = paginatorService;
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public String showItems(
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "amountElement", required = false, defaultValue = "10") String amountElement,
            Model model) {
        Long sizeList = itemService.getAmountItems();
        Paginator paginator = paginatorService.get(page, amountElement, sizeList);
        Integer firstElement = (paginator.getPage() - 1) * paginator.getAmountElementOnPage();
        List<ItemDTO> items = itemService.getItems(firstElement, paginator.getAmountElementOnPage());
        model.addAttribute("items", items);
        model.addAttribute("paginator", paginator);
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
