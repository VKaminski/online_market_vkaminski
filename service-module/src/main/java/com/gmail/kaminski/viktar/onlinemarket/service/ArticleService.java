package com.gmail.kaminski.viktar.onlinemarket.service;

import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.util.ArticlesRequestDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.NewArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.PageDTO;

import java.util.Date;
import java.util.List;

public interface ArticleService {
    PageDTO<ArticleDTO> getArticlesPage(ArticlesRequestDTO request, PageDTO<ArticleDTO> pageDTO);

    Integer getAmountArticles();

    ArticleDTO getById(Long id);

    void add(NewArticleDTO newArticleDTO);

    void delete(Long id);

    void update(ArticleDTO articleDTO);
}
