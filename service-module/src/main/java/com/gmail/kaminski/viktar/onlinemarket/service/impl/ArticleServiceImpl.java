package com.gmail.kaminski.viktar.onlinemarket.service.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.ArticleRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.CommentRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.exception.ArticleRepositoryException;
import com.gmail.kaminski.viktar.onlinemarket.repository.exception.CommentRepositoryException;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Article;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Comment;
import com.gmail.kaminski.viktar.onlinemarket.service.ArticleService;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.ArticleConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.CommentConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.exception.ArticleServiceException;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.CommentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");
    @Value("${custom.comment.forbidden.word}")
    private String forbiddenWords;
    private ArticleRepository articleRepository;
    private ArticleConverter articleConverter;
    private CommentRepository commentRepository;
    private CommentConverter commentConverter;

    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleConverter articleConverter, CommentRepository commentRepository, CommentConverter commentConverter) {
        this.articleRepository = articleRepository;
        this.articleConverter = articleConverter;
        this.commentRepository = commentRepository;
        this.commentConverter = commentConverter;
    }

    @Override
    public Long getAmountArticles() {
        try (Connection connection = articleRepository.getConnection()) {
            connection.setAutoCommit(false);
            Long amountArticles = articleRepository.getAmountArticles(connection);
            connection.commit();
            return amountArticles;
        } catch (SQLException e) {
            logger.debug(custom, "Exception in getAmountArticles" + e.getMessage());
            throw new ArticleServiceException(e);
        }
    }

    @Override
    public ArticleDTO getById(Long id) {
        try (Connection connection = articleRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Article article = articleRepository.getById(connection, id);
                List<Comment> comments = commentRepository.getCommentsForArticleById(connection, article.getId());
                article.setComments(comments);
                ArticleDTO articleDTO = articleConverter.toArticleDTO(article);
                connection.commit();
                return articleDTO;
            } catch (ArticleRepositoryException e) {
                connection.rollback();
                logger.debug("Operation getById was rollback");
                throw new ArticleServiceException(e);
            }
        } catch (SQLException e) {
            logger.debug(e.getMessage());
            throw new ArticleServiceException(e);
        }
    }

    @Override
    public boolean addComment(CommentDTO commentDTO) {
        if (!validComment(commentDTO.getContent())) {
            return false;
        }
        try (Connection connection = commentRepository.getConnection()) {
            connection.setAutoCommit(false);
            Comment comment = commentConverter.toComment(commentDTO);
            try {
                commentRepository.add(connection, comment);
                connection.commit();
            } catch (CommentRepositoryException e) {
                connection.rollback();
                logger.error(this.getClass().getName() + "rollback operation in addComment");
                throw new ArticleServiceException(e);
            }
        } catch (SQLException e) {
            logger.error(this.getClass().getName() + "problem with connection in addComment");
            throw new ArticleServiceException(e);
        }
        return true;
    }

    private boolean validComment(String content) {
        String[] forbiddenArray = forbiddenWords.split(",");
        for (String word : forbiddenArray) {
            if(content.contains(word)){
                return false;
            }
        }
        return true;
    }

    @Override
    public List<ArticleDTO> findByTitle(String searchRequest, Long firstElement, Integer amountElement) {
        try (Connection connection = articleRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Article> articles = articleRepository.findByTitle(connection, searchRequest, firstElement, amountElement);
                List<ArticleDTO> output = new ArrayList<>();
                for (Article article : articles) {
                    output.add(articleConverter.toArticleDTO(article));
                }
                connection.commit();
                return output;
            } catch (ArticleRepositoryException e) {
                logger.debug(custom, this.getClass().getName() + "rollback operation in getArticles");
                connection.rollback();
                throw new ArticleServiceException(e);
            }
        } catch (SQLException e) {
            logger.debug(custom, this.getClass().getName() + "problem with connection in getArticles");
            throw new ArticleServiceException(e);
        }
    }

    @Override
    public List<ArticleDTO> findByDate(String dateStart, String dateStop, Long firstElement, Integer amountElement) {
        try (Connection connection = articleRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Article> articles = articleRepository.findByDate(connection, dateStart, dateStop, firstElement, amountElement);
                List<ArticleDTO> output = new ArrayList<>();
                for (Article article : articles) {
                    output.add(articleConverter.toArticleDTO(article));
                }
                connection.commit();
                return output;
            } catch (ArticleRepositoryException e) {
                logger.debug(custom, this.getClass().getName() + "rollback operation in findByDate");
                connection.rollback();
                throw new ArticleServiceException(e);
            }
        } catch (SQLException e) {
            logger.debug(custom, this.getClass().getName() + "problem with connection in findByDate");
            throw new ArticleServiceException(e);
        }
    }

    @Override
    public List<ArticleDTO> getArticles(Long firstElement, Integer amountElement) {
        try (Connection connection = articleRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Article> articles = articleRepository.getArticles(connection, firstElement, amountElement);
                List<ArticleDTO> output = new ArrayList<>();
                for (Article article : articles) {
                    output.add(articleConverter.toArticleDTO(article));
                }
                connection.commit();
                return output;
            } catch (ArticleRepositoryException e) {
                logger.debug(custom, this.getClass().getName() + "rollback operation in getArticles");
                connection.rollback();
                throw new ArticleServiceException(e);
            }
        } catch (SQLException e) {
            logger.debug(custom, this.getClass().getName() + "problem with connection in getArticles");
            throw new ArticleServiceException(e);
        }
    }
}
