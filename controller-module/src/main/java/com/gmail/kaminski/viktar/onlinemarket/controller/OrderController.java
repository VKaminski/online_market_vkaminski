package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.controller.config.GlobalValue;
import com.gmail.kaminski.viktar.onlinemarket.controller.exception.WebControllerException;
import com.gmail.kaminski.viktar.onlinemarket.controller.util.RequestParamService;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.enums.OrderStatusEnum;
import com.gmail.kaminski.viktar.onlinemarket.service.OrderService;
import com.gmail.kaminski.viktar.onlinemarket.service.ProfileService;
import com.gmail.kaminski.viktar.onlinemarket.service.model.AppUserPrincipal;
import com.gmail.kaminski.viktar.onlinemarket.service.model.OrderDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.PageDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ProfileDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserAuthorizedDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OrderController {
    private RequestParamService requestParamService;
    private OrderService orderService;
    private ProfileService profileService;
    private GlobalValue globalValue;

    public OrderController(RequestParamService requestParamService,
                           OrderService orderService,
                           ProfileService profileService,
                           GlobalValue globalValue) {
        this.requestParamService = requestParamService;
        this.orderService = orderService;
        this.profileService = profileService;
        this.globalValue = globalValue;
    }

    @GetMapping("/orders")
    public String getOrders(
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "amountElement", required = false, defaultValue = "10") String amountElement,
            Model model) {
        PageDTO<OrderDTO> ordersPage = new PageDTO<>();
        ordersPage.setPage(requestParamService.getInteger(page, Integer.MAX_VALUE, globalValue.getDefaultPage()));
        ordersPage.setAmountElementsOnPage(
                requestParamService.getInteger(
                        amountElement,
                        globalValue.getDefaultMaxAmountElements(),
                        globalValue.getDefaultAmountElements()));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUserPrincipal userPrincipal = (AppUserPrincipal) auth.getPrincipal();
        UserAuthorizedDTO authorizedUser = userPrincipal.getAuthorizedUserDTO();
        try {
            if (authorizedUser.getRole().getName().equals("ROLE_" + globalValue.getSaleRoleName())) {
                orderService.getOrdersPage(ordersPage, null);
            } else {
                orderService.getOrdersPage(ordersPage, authorizedUser.getId());
            }
            model.addAttribute("ordersPage", ordersPage);
            return "orders";
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Orders were not found", e);
        }
    }

    @GetMapping("/orders/{id}")
    public String getOrder(
            @PathVariable("id") Long id,
            Model model) {
        try {
            OrderDTO orderDTO = orderService.getById(id);
            ProfileDTO profileDTO = profileService.getById(orderDTO.getCustomer().getId());
            OrderStatusEnum[] statusList = OrderStatusEnum.values();
            model.addAttribute("statusList", statusList);
            model.addAttribute("order", orderDTO);
            model.addAttribute("profile", profileDTO);
            return "order";
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Order was not found", e);
        }
    }

    @PostMapping("/items/{id}/buy")
    public String addOrder(
            RedirectAttributes redirectAttributes,
            @PathVariable("id") Long id,
            @RequestParam("amount") String amount) {
        Integer validAmount = requestParamService.getAmountItems(amount, globalValue.getDefaultAmountForBuy());
        if (validAmount == null){
            redirectAttributes.addFlashAttribute("message", "Incorrect value");
            return "redirect:/items";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUserPrincipal userPrincipal = (AppUserPrincipal) auth.getPrincipal();
        UserAuthorizedDTO authorizedUser = userPrincipal.getAuthorizedUserDTO();
        try {
            orderService.add(id, validAmount, authorizedUser.getId());
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Item was not bought!", e);
        }
        return "redirect:/items";
    }

    @PostMapping("/orders/{id}/switchstatus")
    public String switchOrderStatus(
            @PathVariable("id") Long id,
            @RequestParam String newstatus) {
        try {
            orderService.updateStatus(id, newstatus);
            return "redirect:/orders/{id}";
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Status was not switched!", e);
        }
    }


}
