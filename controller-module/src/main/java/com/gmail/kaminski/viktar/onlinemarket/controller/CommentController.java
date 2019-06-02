package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.service.ArticleService;
import com.gmail.kaminski.viktar.onlinemarket.service.CommentService;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.CommentDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    private ArticleService articleService;
    private ArticleController articleController;
    private CommentService commentService;

    public CommentController(ArticleService articleService,
                             ArticleController articleController,
                             CommentService commentService) {
        this.articleService = articleService;
        this.articleController = articleController;
        this.commentService = commentService;
    }

    @PostMapping("/articles/{id}/comments/{commentId}/addcomment")
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
        if (commentService.addComment(newComment)) {
            return "redirect:/articles/" + articleId;
        } else {
            String errorMessage = "Message contain forbidden word";
            model.addAttribute("errorMessage", errorMessage);
            articleController.showArticle(articleId, model);
            return "article";
        }
    }

    @PostMapping("/articles/{articleId}/comments/{commentId}/delete")
    public String deleteComment(
            @PathVariable("articleId") Long articleId,
            @PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return "redirect:/articles/" + articleId;
    }
}
