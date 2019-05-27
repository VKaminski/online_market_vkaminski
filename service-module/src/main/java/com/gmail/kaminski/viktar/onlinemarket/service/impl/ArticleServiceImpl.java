package com.gmail.kaminski.viktar.onlinemarket.service.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.ArticleRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.CommentRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Article;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Comment;
import com.gmail.kaminski.viktar.onlinemarket.service.ArticleService;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.ArticleConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.CommentConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.CommentDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.NewArticleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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
    @Transactional
    public Long getAmountArticles() {
        return articleRepository.getAmountOfEntities();
    }

    @Override
    @Transactional
    public ArticleDTO getById(Long id) {
        Article article = articleRepository.findById(id);
        return articleConverter.toArticleDTO(article);
    }

    @Override
    @Transactional
    public boolean addComment(CommentDTO commentDTO) {
        if (!validComment(commentDTO.getContent())) {
            return false;
        }
        Comment comment = commentConverter.toComment(commentDTO);
        commentRepository.add(comment);
        return true;
    }

    private boolean validComment(String content) {
        String[] forbiddenArray = forbiddenWords.split(",");
        for (String word : forbiddenArray) {
            if (content.contains(word)) {
                return false;
            }
        }
        return true;
    }

    @Override
    @Transactional
    public List<ArticleDTO> findByTitle(String searchRequest, Integer firstElement, Integer amountElement) {
        List<Article> articles = articleRepository.findByTitle(searchRequest, firstElement, amountElement);
        List<ArticleDTO> output = new ArrayList<>();
        for (Article article : articles) {
            output.add(articleConverter.toArticleDTO(article));
        }
        return output;

    }


    @Override
    @Transactional
    public List<ArticleDTO> findByDate(Date dateStart, Date dateStop, Integer firstElement, Integer amountElement) {
        List<Article> articles = articleRepository.findByDate(dateStart, dateStop, firstElement, amountElement);
        List<ArticleDTO> output = new ArrayList<>();
        for (Article article : articles) {
            output.add(articleConverter.toArticleDTO(article));
        }
        return output;
    }

    @Override
    @Transactional
    public void add(NewArticleDTO newArticleDTO) {
        articleRepository.add(articleConverter.toArticle(newArticleDTO));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Article article = articleRepository.findById(id);
        article.setAuthor(null);
        articleRepository.remove(article);
    }

    @Override
    @Transactional
    public void update(ArticleDTO articleDTO) {
        Article databaseArticle = articleRepository.findById(articleDTO.getId());
        databaseArticle.setTitle(articleDTO.getTitle());
        databaseArticle.setContent(articleDTO.getContent());
        databaseArticle.setDate(new Date(System.currentTimeMillis()));
        articleRepository.update(databaseArticle);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId);
        comment.setAuthor(null);
        comment.setArticle(null);
        comment.setHeadComment(null);
        commentRepository.remove(comment);
    }

    @Override
    @Transactional
    public List<ArticleDTO> getArticles(Integer firstElement, Integer amountElement) {
        List<Article> articles = articleRepository.findAll(firstElement, amountElement);
        List<ArticleDTO> output = new ArrayList<>();
        for (Article article : articles) {
            output.add(articleConverter.toArticleDTO(article));
        }
        return output;
    }
}
