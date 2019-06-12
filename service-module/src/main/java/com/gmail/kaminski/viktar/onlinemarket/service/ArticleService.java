package com.gmail.kaminski.viktar.onlinemarket.service;

import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleEditDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticlePreviewDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.util.ArticlesRequestDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleNewDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.PageDTO;

public interface ArticleService {
    PageDTO<ArticlePreviewDTO> getArticlesPage(ArticlesRequestDTO request, PageDTO<ArticlePreviewDTO> pageDTO);

    Integer getAmountArticles();

    ArticleDTO getById(Long id);

    void add(ArticleNewDTO newArticleDTO);

    void delete(Long id);

    void update(ArticleEditDTO articleDTO);

    ArticleEditDTO getForEditById(Long id);
}
