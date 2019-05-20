package com.gmail.kaminski.viktar.onlinemarket.service.converter;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.Article;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;

public interface ArticleConverter {
    ArticleDTO toArticleDTO(Article article);
}
