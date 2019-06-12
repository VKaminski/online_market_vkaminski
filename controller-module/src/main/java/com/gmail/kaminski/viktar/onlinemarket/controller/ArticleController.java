package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.controller.config.GlobalValue;
import com.gmail.kaminski.viktar.onlinemarket.controller.exception.WebControllerException;
import com.gmail.kaminski.viktar.onlinemarket.controller.util.RequestParamService;
import com.gmail.kaminski.viktar.onlinemarket.service.ArticleService;
import com.gmail.kaminski.viktar.onlinemarket.service.model.AppUserPrincipal;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleEditDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleNewDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticlePreviewDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.CommentNewDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.PageDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserAuthorizedDTO;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");
    private GlobalValue globalValue;
    private ArticleService articleService;
    private RequestParamService requestParamService;

    public ArticleController(GlobalValue globalValue,
                             ArticleService articleService,
                             RequestParamService requestParamService) {
        this.globalValue = globalValue;
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
        PageDTO<ArticlePreviewDTO> articlesPage = new PageDTO<>();
        articlesPage.setPage(requestParamService.getInteger(page, Integer.MAX_VALUE, 1));
        articlesPage.setAmountElementsOnPage(
                requestParamService.getInteger(
                        amountElement,
                        100,
                        10));
            articleService.getArticlesPage(articlesRequestDTO, articlesPage);
        model.addAttribute("articlesPage", articlesPage);
        model.addAttribute("searchRequest", searchRequest);
        model.addAttribute("dateRequestStart", dateRequestStart);
        model.addAttribute("dateRequestStop", dateRequestStop);
        return "articles";
    }

    @GetMapping("/articles/{id}")
    public String showArticle(
            @PathVariable("id") Long id,
            Model model) {
        try {
            ArticleDTO articleDTO = articleService.getById(id);
            CommentNewDTO newComment = new CommentNewDTO();
            model.addAttribute("newComment", newComment);
            model.addAttribute("article", articleDTO);
            return "article";
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Article was not found", e);
        }
    }

    @GetMapping("/articles/{id}/delete")
    public String deleteArticle(
            @PathVariable("id") Long id) {
        try {
            articleService.delete(id);
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Article was not found", e);
        }
        return "redirect:/articles";
    }

    @GetMapping("/articles/{id}/edit")
    public String showArticleEditor(
            @PathVariable("id") Long id,
            Model model) {
        try {
            ArticleEditDTO article = articleService.getForEditById(id);
            model.addAttribute("article", article);
            return "editarticle";
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Article was not edit", e);
        }
    }

    @PostMapping("/articles/{id}/edit")
    public String updateArticle(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute("article") ArticleEditDTO article,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editarticle";
        }
        try {
            articleService.update(article);
            return "redirect:/articles/" + id;
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Article was not update", e);
        }
    }

    @GetMapping("/articles/new")
    public String newArticle(Model model) {
            model.addAttribute("article", new ArticleDTO());
            return "newarticle";
    }

    @PostMapping("/articles/new")
    public String createArticle(
            @Valid @ModelAttribute("article") ArticleNewDTO newArticleDTO,
            BindingResult bindingResult,
            @RequestParam(value = "createdDate", required = false) String date) {
        if (bindingResult.hasErrors()) {
            return "newarticle";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUserPrincipal userPrincipal = (AppUserPrincipal) auth.getPrincipal();
        UserAuthorizedDTO authorizedUser = userPrincipal.getAuthorizedUserDTO();
        newArticleDTO.setUserId(authorizedUser.getId());
        newArticleDTO.setDate(requestParamService.getDate(date, globalValue.getDefaultDate()));
        try {
            articleService.add(newArticleDTO);
            return "redirect:/articles";
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Article was not create!", e);
        }
    }
}
