package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.service.ProfileService;
import com.gmail.kaminski.viktar.onlinemarket.service.model.AppUserPrincipal;
import com.gmail.kaminski.viktar.onlinemarket.service.model.AuthorizedUserDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ProfileDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {
    private ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/profile")
    public String showUserProfile(
            Model model) {
        Long id = getAuthUserId();
        ProfileDTO profileDTO = profileService.getById(id);
        model.addAttribute("profile", profileDTO);
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String showUserProfileEditor(
            @ModelAttribute("profile") ProfileDTO profile,
            Model model) {
        Long id = getAuthUserId();
        ProfileDTO profileDTO = profileService.getById(id);
        model.addAttribute("profile", profileDTO);
        return "editprofile";
    }

    @PostMapping("/profile/edit")
    public String editUserProfile(
            @ModelAttribute("profile") ProfileDTO profile,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("repeatNewPassword") String repeatNewPassword,
            Model model) {
        Long id = getAuthUserId();
        if (!newPassword.equals(repeatNewPassword)) {
            ProfileDTO profileDTO = profileService.getById(id);
            model.addAttribute("profile", profileDTO);
            return "editprofile";
        } else {
            UserDTO updated = profile.getUser();
            updated.setId(id);
            updated.setPassword(newPassword);
            profileService.update(profile);
            return "redirect:/profile";
        }
    }

    private Long getAuthUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUserPrincipal userPrincipal = (AppUserPrincipal) auth.getPrincipal();
        AuthorizedUserDTO user = userPrincipal.getAuthorizedUserDTO();
        return user.getId();
    }
}
