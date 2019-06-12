package com.gmail.kaminski.viktar.onlinemarket.service.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.ArticleRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Article;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.request.ArticlesRequest;
import com.gmail.kaminski.viktar.onlinemarket.service.ArticleService;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.ArticleConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.RequestConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleEditDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleNewDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticlePreviewDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.PageDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.util.ArticlesRequestDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.validator.PageValidator;
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
    private PageValidator pageValidator;

    public ArticleServiceImpl(ArticleRepository articleRepository,
                              ArticleConverter articleConverter,
                              RequestConverter requestConverter,
                              PageValidator pageValidator) {
        this.articleRepository = articleRepository;
        this.articleConverter = articleConverter;
        this.requestConverter = requestConverter;
        this.pageValidator = pageValidator;
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
    public PageDTO<ArticlePreviewDTO> getArticlesPage(ArticlesRequestDTO requestDTO, PageDTO<ArticlePreviewDTO> pageDTO) {
        pageDTO.setAmountElements(articleRepository.getAmountOfEntities());
        pageValidator.valid(pageDTO);
        int amountElementsOnPage = pageDTO.getAmountElementsOnPage();
        int firstElement = (pageDTO.getPage() - 1) * pageDTO.getAmountElementsOnPage();
        ArticlesRequest request = requestConverter.toArticlesRequest(requestDTO);
        List<Article> articles = articleRepository.findWithParameter(request, firstElement, amountElementsOnPage);
        List<ArticlePreviewDTO> articleDTOs = new ArrayList<>();
        for (Article article : articles) {
            articleDTOs.add(articleConverter.toArticlePreviewDTO(article));
        }
        pageDTO.setElements(articleDTOs);
        return pageDTO;
    }

    @Override
    @Transactional
    public void add(ArticleNewDTO newArticleDTO) {
        articleRepository.add(articleConverter.toArticle(newArticleDTO));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Article article = articleRepository.findById(id);
        articleRepository.remove(article);
    }

    @Override
    @Transactional
    public void update(ArticleEditDTO articleDTO) {
        Article databaseArticle = articleRepository.findById(articleDTO.getId());
        articleDTO.setDate(new Date(System.currentTimeMillis()));
        articleConverter.update(databaseArticle, articleDTO);
        articleRepository.update(databaseArticle);
    }

    @Override
    @Transactional
    public ArticleEditDTO getForEditById(Long id) {
        Article article = articleRepository.findById(id);
        return articleConverter.toArticleEditDTO(article);
    }

}
