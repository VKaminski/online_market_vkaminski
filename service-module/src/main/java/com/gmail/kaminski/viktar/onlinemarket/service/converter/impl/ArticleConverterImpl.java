package com.gmail.kaminski.viktar.onlinemarket.service.converter.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.Article;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Comment;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.ArticleConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.CommentConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.UserConverter;
<<<<<<< HEAD
import com.gmail.kaminski.viktar.onlinemarket.service.impl.UserServiceImpl;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.CommentDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.NewArticleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class
ArticleConverterImpl implements ArticleConverter {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");
=======
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.CommentDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArticleConverterImpl implements ArticleConverter {
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f
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
<<<<<<< HEAD
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
=======
        if (article.getAuthor() != null) {
            articleDTO.setAuthor(userConverter.toUserDTO(article.getAuthor()));
        }
        if (article.getTitle() != null) {
            articleDTO.setTitle(article.getTitle());
        }
        if (article.getContent() != null) {
            articleDTO.setContent(article.getContent());
        }
        if (article.getDate() != null) {
            articleDTO.setDate(article.getDate());
        }
        if (article.getAmountComments() != null) {
            articleDTO.setAmountComments(article.getAmountComments());
        }

        if (article.getComments() != null) {
            if (!article.getComments().isEmpty()) {
                List<CommentDTO> commentDTOs = new ArrayList<>();
                for (Comment comment : article.getComments()) {
                    commentDTOs.add(commentConverter.toCommentDTO(comment));
                }
                articleDTO.setComments(commentDTOs);
            }
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f
        }
        return articleDTO;
    }

    @Override
    public Article toArticle(ArticleDTO articleDTO) {
        Article article = new Article();
        if (articleDTO.getId() != null) {
            article.setId(articleDTO.getId());
        }
<<<<<<< HEAD
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
    public Article toArticle(NewArticleDTO newArticleDTO){
        Article article = new Article();
        article.setAuthor(userConverter.toUser(newArticleDTO.getAuthor()));
        article.setTitle(newArticleDTO.getTitle());
        article.setContent(newArticleDTO.getContent());
        if (newArticleDTO.getDate() != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = format.parse(newArticleDTO.getDate());
            } catch (ParseException e) {
                logger.debug(custom, e.getMessage() + " date: " + newArticleDTO.getDate());
                Long currentTime = System.currentTimeMillis();
                logger.debug(custom, e.getMessage() + " set current date: " + currentTime);
                date = new Date(currentTime);
            }
            article.setDate(date);
=======
        if (articleDTO.getAuthor() != null) {
            article.setAuthor(userConverter.toUser(articleDTO.getAuthor()));
        }
        if (articleDTO.getTitle() != null) {
            article.setTitle(articleDTO.getTitle());
        }
        if (articleDTO.getContent() != null) {
            article.setContent(articleDTO.getContent());
        }
        if (articleDTO.getDate() != null) {
            article.setDate(articleDTO.getDate());
        }
        if (articleDTO.getAmountComments() != null) {
            article.setAmountComments(articleDTO.getAmountComments());
        }

        if (articleDTO.getComments() != null) {
            if (!article.getComments().isEmpty()) {
                List<Comment> comments = new ArrayList<>();
                for (CommentDTO commentDTO : articleDTO.getComments()) {
                    comments.add(commentConverter.toComment(commentDTO));
                }
                article.setComments(comments);
            }
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f
        }
        return article;
    }
}
