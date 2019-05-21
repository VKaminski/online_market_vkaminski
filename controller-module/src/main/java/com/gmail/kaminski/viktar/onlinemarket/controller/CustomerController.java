package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.controller.model.Paginator;
import com.gmail.kaminski.viktar.onlinemarket.controller.util.PaginatorService;
import com.gmail.kaminski.viktar.onlinemarket.service.ArticleService;
import com.gmail.kaminski.viktar.onlinemarket.service.ProfileService;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.CommentDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ProfileDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");
    private ArticleService articleService;
    private PaginatorService paginatorService;
    private ProfileService profileService;

    public CustomerController(ArticleService articleService, PaginatorService paginatorService, ProfileService profileService) {
        this.articleService = articleService;
        this.paginatorService = paginatorService;
        this.profileService = profileService;
    }

    @GetMapping("/articles")
    public String showArticles(
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "amountElement", required = false, defaultValue = "10") String amountElement,
            Model model) {
        logger.debug(custom, "page: " + page + " amountElement: " + amountElement);
        Long sizeList = articleService.getAmountArticles();
        Paginator paginator = paginatorService.get(page, amountElement, sizeList);
        Long firstElement = (paginator.getPage() - 1) * paginator.getAmountElementOnPage();
        List<ArticleDTO> articles = articleService.getArticles(firstElement, paginator.getAmountElementOnPage());
        model.addAttribute("articles", articles);
        model.addAttribute("paginator", paginator);
        return "articles";
    }

    @PostMapping("/articles/findtitle")
    public String findByTitle(
            @RequestParam("searchRequest") String searchRequest,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "amountElement", required = false, defaultValue = "10") String amountElement,
            Model model) {
        logger.debug(custom, "page: " + page + " amountElement: " + amountElement);
        Long sizeList = articleService.getAmountArticles();
        Paginator paginator = paginatorService.get(page, amountElement, sizeList);
        Long firstElement = (paginator.getPage() - 1) * paginator.getAmountElementOnPage();
        List<ArticleDTO> articles = articleService.findByTitle(searchRequest, firstElement, paginator.getAmountElementOnPage());
        model.addAttribute("articles", articles);
        model.addAttribute("paginator", paginator);
        return "articles";
    }

    @PostMapping("/articles/finddate")
    public String findByDate(
            @RequestParam("dateRequestStart") String dateRequestStart,
            @RequestParam("dateRequestStop") String dateRequestStop,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "amountElement", required = false, defaultValue = "10") String amountElement,
            Model model) {
        logger.debug(custom, "page: " + page + " amountElement: " + amountElement);
        Long sizeList = articleService.getAmountArticles();
        Paginator paginator = paginatorService.get(page, amountElement, sizeList);
        Long firstElement = (paginator.getPage() - 1) * paginator.getAmountElementOnPage();
        List<ArticleDTO> articles = articleService.findByDate(dateRequestStart, dateRequestStop, firstElement, paginator.getAmountElementOnPage());
        model.addAttribute("articles", articles);
        model.addAttribute("paginator", paginator);
        return "articles";
    }

    @GetMapping("/articles/{id}")
    public String showArticle(
            @PathVariable("id") Long id,
            Model model) {
        ArticleDTO articleDTO = articleService.getById(id);
        CommentDTO newComment = new CommentDTO();
        model.addAttribute("newComment", newComment);
        model.addAttribute("article", articleDTO);
        return "article";
    }

    @PostMapping("/articles/{id}/{commentId}/addcomment")
    public String addComment(
            @PathVariable("id") Long articleId,
            @PathVariable("commentId") Long commentId,
            @ModelAttribute("newComment") CommentDTO newComment,
            Model model) {
        newComment.setArticleId(articleId);
        newComment.setHead(commentId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = auth.getName();
        UserDTO currentUser = new UserDTO();
        currentUser.setEmail(currentUserEmail);
        newComment.setAuthor(currentUser);
        if (articleService.addComment(newComment)) {
            return "redirect:/articles/" + articleId;
        } else {
            String errorMessage = "Message contain forbidden word";
            model.addAttribute("errorMessage", errorMessage);
            showArticle(articleId, model);
            return "article";
        }
    }

    @GetMapping("/profile")
    public String showUserProfile(
            Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        ProfileDTO profileDTO = profileService.getByUserEmail(email);
        model.addAttribute("profile", profileDTO);
        return "profile";
    }
}
