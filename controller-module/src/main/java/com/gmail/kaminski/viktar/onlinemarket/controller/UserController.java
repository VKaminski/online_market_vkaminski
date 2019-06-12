package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.controller.config.GlobalValue;
import com.gmail.kaminski.viktar.onlinemarket.controller.exception.WebControllerException;
import com.gmail.kaminski.viktar.onlinemarket.controller.model.CheckedUsers;
import com.gmail.kaminski.viktar.onlinemarket.controller.util.RequestParamService;
import com.gmail.kaminski.viktar.onlinemarket.service.RoleService;
import com.gmail.kaminski.viktar.onlinemarket.service.UserService;
import com.gmail.kaminski.viktar.onlinemarket.service.model.PageDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.RoleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserNewDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");
    private GlobalValue globalValue;
    private UserService userService;
    private RoleService roleService;
    private RequestParamService paginatorService;
    private RequestParamService requestParamService;

    protected UserController(GlobalValue globalValue,
                             UserService userService,
                             RoleService roleService,
                             RequestParamService paginatorService,
                             RequestParamService requestParamService) {
        this.globalValue = globalValue;
        this.userService = userService;
        this.roleService = roleService;
        this.paginatorService = paginatorService;
        this.requestParamService = requestParamService;
    }

    @GetMapping("/users")
    public String users(
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "amountElement", required = false, defaultValue = "10") String amountElement,
            Model model) {
        logger.debug(custom, "page: " + page + " amountElement: " + amountElement);
        List<RoleDTO> roles = roleService.getAll();
        PageDTO<UserDTO> usersPage = new PageDTO<>();
        usersPage.setPage(requestParamService.getInteger(page, Integer.MAX_VALUE, globalValue.getDefaultPage()));
        usersPage.setAmountElementsOnPage(
                requestParamService.getInteger(
                        amountElement,
                        globalValue.getDefaultMaxAmountElements(),
                        globalValue.getDefaultAmountElements()));
        try {
            userService.getUsersPage(usersPage);
            List<Long> checkedUsersId = new ArrayList<>();
            CheckedUsers checkedUsers = new CheckedUsers();
            checkedUsers.setCheckedUsersId(checkedUsersId);
            model.addAttribute("roles", roles);
            model.addAttribute("usersPage", usersPage);
            model.addAttribute("checkedUsers", checkedUsers);
            return "users";
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Users were not found!", e);
        }
    }

    @PostMapping("/users/{id}/changerole")
    public String changeRole(@PathVariable("id") Long id,
                             @RequestParam Long roleId) {
        try {
            userService.updateRole(id, roleId);
            return "redirect:/users";
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Role for user was not change!", e);
        }
    }

    @PostMapping("/users/{id}/changepassword")
    public String changePassword(@PathVariable("id") Long id) {
        try {
            userService.changePassword(id, globalValue.getMinLengthPassword(),globalValue.getMaxLengthPassword());
            return "redirect:/users";
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Password for user was not change!", e);
        }
    }

    @PostMapping("/users/delete")
    public String deleteUsers(@ModelAttribute(value = "checkedUsers") CheckedUsers checkedUsers) {
        List<Long> checkedUsersId = checkedUsers.getCheckedUsersId();
        try {
            userService.delete(checkedUsersId);
            return "redirect:/users";
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Users were not deleted!", e);
        }
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        List<RoleDTO> roles = roleService.getAll();
        UserNewDTO user = new UserNewDTO();
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        return "newuser";
    }

    @PostMapping("/users/new")
    public String createUser(
            @Valid @ModelAttribute("user") UserNewDTO userDTO,
            BindingResult bindingResult,
            Model model) {
        List<RoleDTO> roles = roleService.getAll();
        model.addAttribute("roles", roles);
        if (bindingResult.hasErrors()) {
            return "newuser";
        }
        try {
            if (userService.add(userDTO)) {
                return "redirect:/users/new";
            } else {
                model.addAttribute("checkEmail", "Email is not free! Choose another!");
                return "newuser";
            }
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! User was not created!", e);
        }
    }
}
