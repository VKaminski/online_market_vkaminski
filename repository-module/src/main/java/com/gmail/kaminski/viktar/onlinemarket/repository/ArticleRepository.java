package com.gmail.kaminski.viktar.onlinemarket.repository;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.Article;

import java.sql.Connection;
import java.util.List;

public interface ArticleRepository extends GenericRepository {
    Long getAmountArticles(Connection connection);

    Article getById(Connection connection, Long id);

    List<Article> getArticles(Connection connection, Long firstElement, Integer amountElement);

    List<Article> findByTitle(Connection connection, String searchRequest, Long firstElement, Integer amountElement);

    List<Article> findByDate(Connection connection, String dateStart, String dateStop, Long firstElement, Integer amountElement);
}
