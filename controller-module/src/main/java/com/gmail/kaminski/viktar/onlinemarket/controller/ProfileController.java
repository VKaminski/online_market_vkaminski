package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.controller.exception.WebControllerException;
import com.gmail.kaminski.viktar.onlinemarket.service.ProfileService;
import com.gmail.kaminski.viktar.onlinemarket.service.model.AppUserPrincipal;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ProfileDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ProfileEditDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserAuthorizedDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class ProfileController {
    private ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/profile")
    public String showUserProfile(Model model) {
        Long id = getAuthUserId();
        try {
            ProfileDTO profileDTO = profileService.getById(id);
            model.addAttribute("profile", profileDTO);
            return "profile";
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Profile was not found!", e);
        }
    }

    @GetMapping("/profile/edit")
    public String showUserProfileEditor(Model model) {
        Long id = getAuthUserId();
        try {
            ProfileEditDTO profile = profileService.getForEditById(id);
            model.addAttribute("profile", profile);
            return "editprofile";
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Profile was not found!", e);
        }
    }

    @PostMapping("/profile/edit")
    public String editUserProfile(
            @Valid @ModelAttribute("profile") ProfileEditDTO profile,
            BindingResult bindingResult,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("repeatNewPassword") String repeatNewPassword) {
        if (bindingResult.hasErrors()) {
            return "editprofile";
        }
        try {
            Long id = getAuthUserId();
            profile.setId(id);
            if (newPassword.isEmpty()) {
                profileService.update(profile, null);
                return "redirect:/profile";
            } else {
                if (newPassword.equals(repeatNewPassword)) {
                    profileService.update(profile, newPassword);
                    return "redirect:/profile";
                } else {
                    return "editprofile";
                }
            }
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Profile was not updated!", e);
        }
    }

    private Long getAuthUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUserPrincipal userPrincipal = (AppUserPrincipal) auth.getPrincipal();
        UserAuthorizedDTO user = userPrincipal.getAuthorizedUserDTO();
        return user.getId();
    }
}
