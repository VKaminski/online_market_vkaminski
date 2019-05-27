package com.gmail.kaminski.viktar.onlinemarket.repository.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.ArticleRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Article;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Repository
public class ArticleRepositoryImpl extends GenericRepositoryImpl<Long, Article> implements ArticleRepository {

    @Override
    public Article findById(Long id) {
        String hqlRequest = "from Article A where A.id = :id order by date desc";
        Query query = entityManager.createQuery(hqlRequest);
        query.setParameter("id", id);
        return (Article) query.getSingleResult();
    }

    @Override
    public List<Article> findAll(int firstElement, int amountElement) {
        String hqlRequest = "from Article order by date desc";
        Query query = entityManager.createQuery(hqlRequest)
                .setFirstResult(firstElement)
                .setMaxResults(amountElement);
        return query.getResultList();
    }

    @Override
    public List<Article> findByTitle(String searchRequest, Integer firstElement, Integer amountElement) {
        String hqlRequest = "select A from Article A where A.title like concat('%', :searchRequest, '%') order by A.date";
        Query query = entityManager.createQuery(hqlRequest)
                .setFirstResult(firstElement)
                .setMaxResults(amountElement);
        query.setParameter("searchRequest", searchRequest);
        return query.getResultList();
    }

    @Override
    public List<Article> findByDate(Date dateStart, Date dateStop, Integer firstElement, Integer amountElement) {
        String hqlRequest = "select A from Article A where A.date between :dateStart and :dateStop";
        Query query = entityManager.createQuery(hqlRequest)
                .setFirstResult(firstElement)
                .setMaxResults(amountElement);
        query.setParameter("dateStart", dateStart);
        query.setParameter("dateStop", dateStop);
        return query.getResultList();
    }
}
