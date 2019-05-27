package com.gmail.kaminski.viktar.onlinemarket.service.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.ArticleRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.CommentRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Article;
import com.gmail.kaminski.viktar.onlinemarket.service.ArticleService;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.ArticleConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.CommentConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.NewArticleDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Value("${custom.comment.forbidden.word}")
    private String forbiddenWords;
    private ArticleRepository articleRepository;
    private ArticleConverter articleConverter;

    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleConverter articleConverter) {
        this.articleRepository = articleRepository;
        this.articleConverter = articleConverter;
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
    public List<ArticleDTO> getArticles(Integer firstElement, Integer amountElement) {
        List<Article> articles = articleRepository.findAll(firstElement, amountElement);
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

}
