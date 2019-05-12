package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.controller.model.DeletedList;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private UserService userService;
    private RoleService roleService;
    private ReviewService reviewService;
    private PaginatorService paginatorService;

    private AdministratorController(UserService userService,
                                    RoleService roleService,
                                    PaginatorService paginatorService,
                                    ReviewService reviewService) {
        this.userService = userService;
        this.roleService = roleService;
        this.paginatorService = paginatorService;
        this.reviewService = reviewService;
    }

    @GetMapping("/users")
    public String users(@RequestParam(value = "page", required = false, defaultValue="1") Long page,
                        @RequestParam(value = "amountElement", required = false, defaultValue="10") Integer amountElement,
                        Model model) {
        Long firstElement = (page - 1) * amountElement;
        List<UserDTO> users = userService.get(firstElement, amountElement);
        Long sizeList = userService.size();
        Paginator paginator = paginatorService.get(page, amountElement, sizeList);
        List<String> roles = roleService.getRoleNames();
        List<Long> checkedUsersId = new ArrayList<>();
        DeletedList deletedList = new DeletedList();
        deletedList.setCheckedUsersId(checkedUsersId);
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        model.addAttribute("deletedList", deletedList);
        model.addAttribute("paginator", paginator);
        return "users";
    }

    @PostMapping("/users/{id}/changerole")
    public String changeRole(@PathVariable("id") Long id,
                             @RequestParam String role) {
        userService.setRole(id, role);
        return "redirect:/users";
    }

    @PostMapping("/users/{id}/changepassword")
    public String changePassword(@PathVariable("id") Long id) {
        userService.changePassword(id, 5, 20);
        return "redirect:/users";
    }

    @PostMapping("/users/delete")
    public String deleteUsers(@ModelAttribute(value = "deletedList") DeletedList deletedList) {
        List<Long> checkedUsersId = deletedList.getCheckedUsersId();
        userService.delete(checkedUsersId);
        return "redirect:/users";
    }

    @GetMapping("/users/new")
    public String newUser(Model model){
        List<String> roles = roleService.getRoleNames();
        NewUserDTO user = new NewUserDTO();
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        return "new";
    }

    @PostMapping("/users/new/create")
    public String createUser(
            @Valid @ModelAttribute("user") NewUserDTO user) {
        userService.add(user);
        return "redirect:/users/new";
    }

    @GetMapping("/reviews")
    public String reviews(@RequestParam(value = "page", required = false, defaultValue="1") Long page,
                        @RequestParam(value = "amountElement", required = false, defaultValue="10") Integer amountElement,
                        Model model) {
        Long firstElement = (page - 1) * amountElement;
        List<ReviewDTO> reviews = reviewService.get(firstElement, amountElement);
        Long sizeList = reviewService.size();
        Paginator paginator = paginatorService.get(page, amountElement, sizeList);
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
