package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.controller.model.Paginator;
import com.gmail.kaminski.viktar.onlinemarket.controller.util.PaginatorService;
import com.gmail.kaminski.viktar.onlinemarket.service.ArticleService;
import com.gmail.kaminski.viktar.onlinemarket.service.model.AppUserPrincipal;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.CommentDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.NewArticleDTO;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");
    private ArticleService articleService;
    private PaginatorService paginatorService;

    public ArticleController(ArticleService articleService,
                             PaginatorService paginatorService) {
        this.articleService = articleService;
        this.paginatorService = paginatorService;
    }

    @RequestMapping({"/articles", "/articles/findtitle", "/articles/finddate"})
    private String getArticles(
            @RequestParam(value = "searchRequest", required = false) String searchRequest,
            @RequestParam(value = "dateRequestStart", required = false) String dateRequestStart,
            @RequestParam(value = "dateRequestStop", required = false) String dateRequestStop,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "amountElement", required = false, defaultValue = "10") String amountElement,
            Model model) throws ParseException {
        logger.debug(custom, "page: " + page + " amountElement: " + amountElement);
        Long sizeList = articleService.getAmountArticles();
        Paginator paginator = paginatorService.get(page, amountElement, sizeList);
        Integer firstElement = (paginator.getPage() - 1) * paginator.getAmountElementOnPage();
        List<ArticleDTO> articles;
        if (dateRequestStart != null && dateRequestStop != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date dateStart = format.parse(dateRequestStart);
            Date dateStop = format.parse(dateRequestStop);
            articles = articleService.findByDate(dateStart, dateStop, firstElement, paginator.getAmountElementOnPage());
        } else if (searchRequest != null) {
            articles = articleService.findByTitle(searchRequest, firstElement, paginator.getAmountElementOnPage());
        } else {
            articles = articleService.getArticles(firstElement, paginator.getAmountElementOnPage());
        }
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
            @Valid @ModelAttribute("article") NewArticleDTO newArticle) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUserPrincipal userPrincipal = (AppUserPrincipal) auth.getPrincipal();
        UserDTO user = userPrincipal.getUserDTO();
        newArticle.setAuthor(user);
        articleService.add(newArticle);
        return "redirect:/articles";
    }

    @PostMapping("/articles/{id}/{commentId}/addcomment")
    public String addComment(
            @PathVariable("id") Long articleId,
            @PathVariable("commentId") Long commentId,
            @ModelAttribute("newComment") CommentDTO newComment,
            Model model) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(articleId);
        newComment.setArticle(articleDTO);
        CommentDTO headComment = new CommentDTO();
        headComment.setId(commentId);
        newComment.setHeadComment(headComment);
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

    @PostMapping("/articles/{articleId}/{commentId}/delete")
    public String deleteComment(
            @PathVariable("articleId") Long articleId,
            @PathVariable("commentId") Long commentId) {
        articleService.deleteComment(commentId);
        return "redirect:/articles/" + articleId;
    }
}
