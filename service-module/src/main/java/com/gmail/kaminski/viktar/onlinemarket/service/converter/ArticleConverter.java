package com.gmail.kaminski.viktar.onlinemarket.service.converter;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Article;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleEditDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticlePreviewDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleNewDTO;

public interface ArticleConverter {
    ArticleDTO toArticleDTO(Article article);

    Article toArticle(ArticleDTO articleDTO);

    Article toArticle(ArticleNewDTO newArticleDTO);

    ArticlePreviewDTO toArticlePreviewDTO(Article article);

    ArticleEditDTO toArticleEditDTO(Article article);

    void update(Article recipient, ArticleEditDTO donor);
}
