package com.gmail.kaminski.viktar.onlinemarket.service.converter;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.Article;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.NewArticleDTO;

import java.text.ParseException;

public interface ArticleConverter {
    ArticleDTO toArticleDTO(Article article);

    Article toArticle(ArticleDTO articleDTO);

    Article toArticle(NewArticleDTO newArticleDTO);
}
