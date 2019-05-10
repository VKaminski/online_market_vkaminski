package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.service.UserService;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdministratorController {
    private static final Logger logger = LoggerFactory.getLogger(AdministratorController.class);
    private UserService userService;

    private AdministratorController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String showItems(Model model) {
        List<UserDTO> users = userService.getUsers();
        model.addAttribute("users", users);
        return "users";
    }
}
