package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.controller.exception.WebControllerException;
import com.gmail.kaminski.viktar.onlinemarket.service.CommentService;
import com.gmail.kaminski.viktar.onlinemarket.service.model.AppUserPrincipal;
import com.gmail.kaminski.viktar.onlinemarket.service.model.CommentNewDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserAuthorizedDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    private ArticleController articleController;
    private CommentService commentService;

    public CommentController(ArticleController articleController,
                             CommentService commentService) {
        this.articleController = articleController;
        this.commentService = commentService;
    }

    @PostMapping("/articles/{id}/comments/add")
    public String addComment(
            @PathVariable("id") Long articleId,
            @ModelAttribute("newComment") CommentNewDTO newComment,
            Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUserPrincipal userPrincipal = (AppUserPrincipal) auth.getPrincipal();
        UserAuthorizedDTO authorizedUser = userPrincipal.getAuthorizedUserDTO();
        newComment.setAuthorId(authorizedUser.getId());
        newComment.setArticleId(articleId);
        try {
            if (commentService.addComment(newComment)) {
                return "redirect:/articles/" + articleId;
            } else {
                String errorMessage = "Message contain forbidden word";
                model.addAttribute("errorMessage", errorMessage);
                articleController.showArticle(articleId, model);
                return "redirect:/articles/" + articleId;
            }
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Comment was not create", e);
        }
    }

    @PostMapping("/articles/{articleId}/comments/{commentId}/delete")
    public String deleteComment(
            @PathVariable("articleId") Long articleId,
            @PathVariable("commentId") Long commentId) {
        try {
            commentService.deleteComment(commentId);
            return "redirect:/articles/" + articleId;
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Comment was not deleted", e);
        }
    }
}
