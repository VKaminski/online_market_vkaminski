package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.controller.util.RequestParamService;
import com.gmail.kaminski.viktar.onlinemarket.service.ArticleService;
import com.gmail.kaminski.viktar.onlinemarket.service.model.AppUserPrincipal;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.AuthorizedUserDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.CommentDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.NewArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.PageDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.util.ArticlesRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");
    @Value("${custom.date.format}")
    private String dateFormat;
    private ArticleService articleService;
    private RequestParamService requestParamService;

    public ArticleController(ArticleService articleService,
                             RequestParamService requestParamService) {
        this.articleService = articleService;
        this.requestParamService = requestParamService;
    }

    @RequestMapping({"/articles", "/articles/find"})
    public String getArticles(
            @RequestParam(value = "searchRequest", required = false, defaultValue = "") String searchRequest,
            @RequestParam(value = "dateRequestStart", required = false) String dateRequestStart,
            @RequestParam(value = "dateRequestStop", required = false) String dateRequestStop,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "amountElement", required = false, defaultValue = "10") String amountElement,
            Model model) {
        logger.debug(custom, "page: " + page + " amountElement: " + amountElement);
        ArticlesRequestDTO articlesRequestDTO = new ArticlesRequestDTO();
        articlesRequestDTO.setTitle(searchRequest);
        articlesRequestDTO.setDateStart(requestParamService.getDate(dateRequestStart, 0l));
        articlesRequestDTO.setDateStop(requestParamService.getDate(dateRequestStop, System.currentTimeMillis()));
        PageDTO<ArticleDTO> articlesPage = new PageDTO<>();
        articlesPage.setPage(requestParamService.getElements(page, Integer.MAX_VALUE, 1));
        articlesPage.setAmountElementsOnPage(requestParamService.getElements(amountElement, 100, 10));
        articleService.getArticlesPage(articlesRequestDTO, articlesPage);
        model.addAttribute("articlesPage", articlesPage);
        model.addAttribute("searchRequest", searchRequest);
        model.addAttribute("dateRequestStart", dateRequestStart);
        model.addAttribute("dateRequestStop", dateRequestStop);
        return "articles";
    }

    @RequestMapping("/articles/{id}")
    public String showArticle(
            @PathVariable("id") Long id,
            Model model) {
        ArticleDTO articleDTO = articleService.getById(id);
        CommentDTO newComment = new CommentDTO();
        model.addAttribute("newComment", newComment);
        model.addAttribute("article", articleDTO);
        return "article";
    }

    @GetMapping("/articles/{id}/delete")
    public String deleteArticle(
            @PathVariable("id") Long id) {
        articleService.delete(id);
        return "redirect:/articles";
    }

    @PostMapping("/articles/{id}/update")
    public String deleteArticle(
            @ModelAttribute("article") ArticleDTO article) {
        articleService.update(article);
        return "redirect:/articles";
    }

    @GetMapping("/articles/new")
    public String newArticle(Model model) {
        model.addAttribute("article", new ArticleDTO());
        return "newarticle";
    }

    @PostMapping("/articles/new")
    public String createArticle(
            @ModelAttribute("article") NewArticleDTO newArticleDTO,
            @RequestParam(value = "createdDate", required = false) String date) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUserPrincipal userPrincipal = (AppUserPrincipal) auth.getPrincipal();
        AuthorizedUserDTO authorizedUser = userPrincipal.getAuthorizedUserDTO();
        newArticleDTO.setUserId(authorizedUser.getId());
        newArticleDTO.setDate(requestParamService.getDate(date, System.currentTimeMillis()));
        articleService.add(newArticleDTO);
        return "redirect:/articles";
    }
}
