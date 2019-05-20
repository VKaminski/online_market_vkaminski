package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.controller.model.CheckedUsers;
import com.gmail.kaminski.viktar.onlinemarket.controller.model.Paginator;
import com.gmail.kaminski.viktar.onlinemarket.controller.util.PaginatorService;
import com.gmail.kaminski.viktar.onlinemarket.service.ReviewService;
import com.gmail.kaminski.viktar.onlinemarket.service.RoleService;
import com.gmail.kaminski.viktar.onlinemarket.service.UserService;
import com.gmail.kaminski.viktar.onlinemarket.service.model.NewUserDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ReviewDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;
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
public class AdministratorController {
    private static final Logger logger = LoggerFactory.getLogger(AdministratorController.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");
    private UserService userService;
    private RoleService roleService;
    private ReviewService reviewService;
    private PaginatorService paginatorService;

    protected AdministratorController(UserService userService,
                                      RoleService roleService,
                                      PaginatorService paginatorService,
                                      ReviewService reviewService) {
        this.userService = userService;
        this.roleService = roleService;
        this.paginatorService = paginatorService;
        this.reviewService = reviewService;
    }

    @GetMapping("/users")
    public String users(
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "amountElement", required = false, defaultValue = "10") String amountElement,
            Model model) {
        Long sizeList = userService.getAmountUsers();
        Paginator paginator = paginatorService.get(page, amountElement, sizeList);
        Long firstElement = (paginator.getPage() - 1) * paginator.getAmountElementOnPage();
        List<UserDTO> users = userService.getUsers(firstElement, paginator.getAmountElementOnPage());
        List<String> roles = roleService.getRoleNames();
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
                             @RequestParam String role) {
        userService.updateRole(id, role);
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
        List<String> roles = roleService.getRoleNames();
        NewUserDTO user = new NewUserDTO();
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        return "new";
    }

    @PostMapping("/users/new/create")
    public String createUser(
            @Valid @ModelAttribute("user") NewUserDTO user,
            BindingResult bindingResult,
            Model model) {
        List<String> roles = roleService.getRoleNames();
        model.addAttribute("roles", roles);
        if (bindingResult.hasErrors()) {
            return "new";
        }
        userService.add(user);
        return "redirect:/users/new";
    }

    @GetMapping("/reviews")
    public String reviews(@RequestParam(value = "page", required = false, defaultValue = "1") String page,
                          @RequestParam(value = "amountElement", required = false, defaultValue = "10") String amountElement,
                          Model model) {
        Long sizeList = reviewService.size();
        Paginator paginator = paginatorService.get(page, amountElement, sizeList);
        Long firstElement = (paginator.getPage() - 1) * paginator.getAmountElementOnPage();
        List<ReviewDTO> reviews = reviewService.get(firstElement, paginator.getAmountElementOnPage());
        Long id = new Long(0);
        model.addAttribute("reviewId", id);
        model.addAttribute("reviews", reviews);
        model.addAttribute("paginator", paginator);
        return "reviews";
    }

    @PostMapping("/reviews/{id}/hide")
    public String makeVisibleReview(@PathVariable("id") Long id) {
        reviewService.show(id);
        return "redirect:/reviews";
    }

    @PostMapping("/reviews/{id}/delete")
    public String deleteReview(@PathVariable("id") Long id) {
        reviewService.delete(id);
        return "redirect:/reviews";
    }

}
