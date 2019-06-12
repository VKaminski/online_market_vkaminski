package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.controller.config.GlobalValue;
import com.gmail.kaminski.viktar.onlinemarket.controller.exception.WebControllerException;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class ItemController {
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");
    private GlobalValue globalValue;
    private ItemService itemService;
    private RequestParamService requestParamService;

    public ItemController(GlobalValue globalValue,
                          ItemService itemService,
                          RequestParamService requestParamService) {
        this.globalValue = globalValue;
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
        itemsPage.setPage(requestParamService.getInteger(page, Integer.MAX_VALUE, globalValue.getDefaultPage()));
        itemsPage.setAmountElementsOnPage(
                requestParamService.getInteger(
                        amountElement,
                        globalValue.getDefaultMaxAmountElements(),
                        globalValue.getDefaultAmountElements()));
        try {
            itemService.getItemsPage(itemsPage);
            model.addAttribute("itemsPage", itemsPage);
            return "items";
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Items were not found", e);
        }
    }

    @GetMapping("/items/{id}")
    public String showArticle(
            @PathVariable("id") Long id,
            Model model) {
        try {
            ItemDTO itemDTO = itemService.getById(id);
            model.addAttribute("item", itemDTO);
            return "item";
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Item was not founded", e);
        }
    }

    @GetMapping("/items/{id}/delete")
    public String deleteItem(
            @PathVariable("id") Long id) {
        try {
            itemService.delete(id);
            return "redirect:/items";
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Item was not deleted", e);
        }
    }

    @GetMapping("/items/{id}/copy")
    public String copyItem(
            @PathVariable("id") Long id) {
        try {
            itemService.copy(id);
            return "redirect:/items";
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Item was not copied", e);
        }
    }

    @GetMapping("/items/upload")
    public String showUploadItemsPage() {
        return "upload";
    }

    @PostMapping("/items/upload")
    public String uploadItems(
            RedirectAttributes redirectAttributes,
            @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "redirect:/items/upload";
        }
        try {
            requestParamService.validXMLByScheme(file);
            Map<String, Integer> result = itemService.addFromXML(file);
            StringBuffer message = new StringBuffer();
            for (Map.Entry<String, Integer> resultEntry : result.entrySet()) {
                message.append(resultEntry.getValue()).append(" items was ").append(resultEntry.getKey()).append(System.lineSeparator());
            }
            redirectAttributes.addFlashAttribute("message", message.toString());
            return "redirect:/items/upload";
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Item was not added", e);
        }

    }
}
