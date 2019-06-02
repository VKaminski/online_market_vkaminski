package com.gmail.kaminski.viktar.onlinemarket.repository;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.Article;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.util.ArticlesRequest;

import java.util.List;

public interface ArticleRepository extends GenericRepository<Long, Article> {

    List<Article> findAll(int firstElement, int amountElement);

    List<Article> findWithParameter(ArticlesRequest request, Integer firstElement, Integer amountElement);
}
