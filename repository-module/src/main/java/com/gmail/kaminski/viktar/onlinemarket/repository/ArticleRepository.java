package com.gmail.kaminski.viktar.onlinemarket.repository;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.Article;

import java.util.Date;
import java.util.List;

public interface ArticleRepository extends GenericRepository<Long, Article> {

    List<Article> findAll(int firstElement, int amountElement);

    List<Article> findByTitle(String searchRequest, Integer firstElement, Integer amountElement);

    List<Article> findByDate(Date dateStart, Date dateStop, Integer firstElement, Integer amountElement);
}
