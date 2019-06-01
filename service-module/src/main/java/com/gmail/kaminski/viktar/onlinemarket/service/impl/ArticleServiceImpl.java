package com.gmail.kaminski.viktar.onlinemarket.service.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.ArticleRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Article;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.util.ArticlesRequest;
import com.gmail.kaminski.viktar.onlinemarket.service.ArticleService;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.ArticleConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.RequestConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.NewArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.PageDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.util.ArticlesRequestDTO;
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
    private RequestConverter requestConverter;

    public ArticleServiceImpl(ArticleRepository articleRepository,
                              ArticleConverter articleConverter,
                              RequestConverter requestConverter) {
        this.articleRepository = articleRepository;
        this.articleConverter = articleConverter;
        this.requestConverter = requestConverter;
    }

    @Override
    @Transactional
    public Integer getAmountArticles() {
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
    public PageDTO<ArticleDTO> getArticlesPage(ArticlesRequestDTO requestDTO, PageDTO<ArticleDTO> pageDTO) {
        int amountElements = articleRepository.getAmountOfEntities();
        pageDTO.setAmountElements(amountElements);
        Integer amountElementsOnPage = pageDTO.getAmountElementsOnPage();
        int amountPages = amountElements / amountElementsOnPage + 1;
        if (pageDTO.getPage() > (amountPages)) {
            pageDTO.setPage(amountPages);
        }
        int firstElement = (pageDTO.getPage() - 1) * pageDTO.getAmountElementsOnPage();
        ArticlesRequest request = requestConverter.toArticlesRequest(requestDTO);
        List<Article> articles = articleRepository.findWithParameter(request, firstElement, amountElementsOnPage);
        List<ArticleDTO> articleDTOs = new ArrayList<>();
        for (Article article : articles) {
            articleDTOs.add(articleConverter.toArticleDTO(article));
        }
        pageDTO.setElements(articleDTOs);
        return pageDTO;
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
