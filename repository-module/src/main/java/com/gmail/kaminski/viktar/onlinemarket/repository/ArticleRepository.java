package com.gmail.kaminski.viktar.onlinemarket.repository;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.Article;

<<<<<<< HEAD
import java.util.Date;
import java.util.List;

public interface ArticleRepository extends GenericRepository<Long, Article> {

    List<Article> findAll(int firstElement, int amountElement);

    List<Article> findByTitle(String searchRequest, Integer firstElement, Integer amountElement);

    List<Article> findByDate(Date dateStart, Date dateStop, Integer firstElement, Integer amountElement);
=======
import java.sql.Connection;
import java.util.List;

public interface ArticleRepository extends GenericRepository {
    Long getAmountArticles(Connection connection);

    Article getById(Connection connection, Long id);

    List<Article> getArticles(Connection connection, Long firstElement, Integer amountElement);

    List<Article> findByTitle(Connection connection, String searchRequest, Long firstElement, Integer amountElement);

    List<Article> findByDate(Connection connection, String dateStart, String dateStop, Long firstElement, Integer amountElement);

    void add(Connection connection, Article article);

    void delete(Connection connection, Long id);
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f
}
