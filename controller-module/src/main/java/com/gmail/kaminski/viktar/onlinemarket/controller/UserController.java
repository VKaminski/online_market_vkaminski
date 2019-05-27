package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.controller.model.CheckedUsers;
import com.gmail.kaminski.viktar.onlinemarket.controller.model.Paginator;
import com.gmail.kaminski.viktar.onlinemarket.controller.util.PaginatorService;
import com.gmail.kaminski.viktar.onlinemarket.service.RoleService;
import com.gmail.kaminski.viktar.onlinemarket.service.UserService;
import com.gmail.kaminski.viktar.onlinemarket.service.model.RoleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;
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
    private UserService userService;
    private RoleService roleService;
    private PaginatorService paginatorService;

    protected UserController(UserService userService,
                             RoleService roleService,
                             PaginatorService paginatorService) {
        this.userService = userService;
        this.roleService = roleService;
        this.paginatorService = paginatorService;
    }

    @GetMapping("/users")
    public String users(
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "amountElement", required = false, defaultValue = "10") String amountElement,
            Model model) {
        Long sizeList = userService.getAmountUsers();
        Paginator paginator = paginatorService.get(page, amountElement, sizeList);
        Integer firstElement = (paginator.getPage() - 1) * paginator.getAmountElementOnPage();
        List<UserDTO> users = userService.getUsers(firstElement, paginator.getAmountElementOnPage());
        List<RoleDTO> roles = roleService.getAll();
        List<Long> checkedUsersId = new ArrayList<>();
        CheckedUsers checkedUsers = new CheckedUsers();
        checkedUsers.setCheckedUsersId(checkedUsersId);
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        model.addAttribute("checkedUsers", checkedUsers);
        model.addAttribute("paginator", paginator);
        return "users";
    }

    @PostMapping("/users/{id}/changerole")
    public String changeRole(@PathVariable("id") Long id,
                             @RequestParam Long roleId) {
        userService.updateRole(id, roleId);
        return "redirect:/users";
    }

    @PostMapping("/users/{id}/changepassword")
    public String changePassword(@PathVariable("id") Long id) {
        userService.changePassword(id, 5, 20);
        return "redirect:/users";
    }

    @PostMapping("/users/delete")
    public String deleteUsers(@ModelAttribute(value = "checkedUsers") CheckedUsers checkedUsers) {
        List<Long> checkedUsersId = checkedUsers.getCheckedUsersId();
        userService.delete(checkedUsersId);
        return "redirect:/users";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        List<RoleDTO> roles = roleService.getAll();
        UserDTO user = new UserDTO();
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        return "newuser";
    }

    @PostMapping("/users/new/create")
    public String createUser(
            @Valid @ModelAttribute("user") UserDTO user,
            BindingResult bindingResult,
            Model model) {
        List<RoleDTO> roles = roleService.getAll();
        model.addAttribute("roles", roles);
        if (bindingResult.hasErrors()) {
            return "newuser";
        }
        userService.add(user);
        return "redirect:/users/new";
    }
}
