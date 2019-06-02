package com.gmail.kaminski.viktar.onlinemarket.service.converter.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.Article;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Comment;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.User;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.ArticleConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.CommentConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.UserConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.impl.UserServiceImpl;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.CommentDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.NewArticleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class
ArticleConverterImpl implements ArticleConverter {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");
    @Value("${custom.date.format}")
    private String dateFormat;
    private UserConverter userConverter;
    private CommentConverter commentConverter;

    public ArticleConverterImpl(UserConverter userConverter, CommentConverter commentConverter) {
        this.userConverter = userConverter;
        this.commentConverter = commentConverter;
    }

    @Override
    public ArticleDTO toArticleDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        if (article.getId() != null) {
            articleDTO.setId(article.getId());
        }
        articleDTO.setAuthor(userConverter.toUserDTO(article.getAuthor()));
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
        if (article.getDate() != null) {
            articleDTO.setDate(article.getDate());
        }

        if (!article.getComments().isEmpty()) {
            List<CommentDTO> commentDTOs = new ArrayList<>();
            for (Comment comment : article.getComments()) {
                commentDTOs.add(commentConverter.toCommentDTO(comment));
            }
            articleDTO.setComments(commentDTOs);
        }
        return articleDTO;
    }

    @Override
    public Article toArticle(ArticleDTO articleDTO) {
        Article article = new Article();
        if (articleDTO.getId() != null) {
            article.setId(articleDTO.getId());
        }
        article.setAuthor(userConverter.toUser(articleDTO.getAuthor()));
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        if (articleDTO.getDate() != null) {
            article.setDate(articleDTO.getDate());
        }
        if (!article.getComments().isEmpty()) {
            List<Comment> comments = new ArrayList<>();
            for (CommentDTO commentDTO : articleDTO.getComments()) {
                comments.add(commentConverter.toComment(commentDTO));
            }
            article.setComments(comments);
        }
        return article;
    }

    @Override
    public Article toArticle(NewArticleDTO newArticleDTO) {
        Article article = new Article();
        User user = new User();
        user.setId(newArticleDTO.getUserId());
        article.setAuthor(user);
        article.setTitle(newArticleDTO.getTitle());
        article.setContent(newArticleDTO.getContent());
        if (newArticleDTO.getDate() != null) {
            article.setDate(newArticleDTO.getDate());
        }
        return article;
    }
}
