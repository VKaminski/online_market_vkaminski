package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.service.RoleService;
import com.gmail.kaminski.viktar.onlinemarket.service.UserService;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdministratorController {
    private static final Logger logger = LoggerFactory.getLogger(AdministratorController.class);
    private UserService userService;
    private RoleService roleService;

    private AdministratorController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<UserDTO> users = userService.getUsers(0l,10);
        List<String> roles = roleService.getRoleNames();
        List<Long> userIds = new ArrayList<>();
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        model.addAttribute("userIds", userIds);
        return "users";
    }

    @PostMapping("/users/{id}/changerole")
    public String changeRole (@PathVariable("id") Long id,
                                @RequestParam String role){
        userService.setRole(id, role);
        return "redirect:/users";
    }

    @PostMapping("/users/delete")
    public String delete(@RequestParam("userIds") List<Long> userIds){
        for (Long userId : userIds) {
            logger.info(String.valueOf(userId));
        }
        return "redirect:/users";
    }

}
