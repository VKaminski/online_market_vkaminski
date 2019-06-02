package com.gmail.kaminski.viktar.onlinemarket.repository.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.ArticleRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Article;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.util.ArticlesRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ArticleRepositoryImpl extends GenericRepositoryImpl<Long, Article> implements ArticleRepository {

    @Override
    public List<Article> findAll(int firstElement, int amountElement) {
        String hqlRequest = "from Article order by date desc";
        Query query = entityManager.createQuery(hqlRequest)
                .setFirstResult(firstElement)
                .setMaxResults(amountElement);
        return query.getResultList();
    }

    @Override
    public Article findById(Long id) {
        String hqlRequest = "from Article A where A.id = :id order by date desc";
        Query query = entityManager.createQuery(hqlRequest);
        query.setParameter("id", id);
        return (Article) query.getSingleResult();
    }

    @Override
    public List<Article> findWithParameter(ArticlesRequest request, Integer firstElement, Integer amountElement) {
        String hqlRequest = "select A from Article A where A.date between :dateStart and :dateStop" +
                " and A.title like concat('%', :searchRequest, '%') order by A.date desc";
        Query query = entityManager.createQuery(hqlRequest)
                .setFirstResult(firstElement)
                .setMaxResults(amountElement);
        query.setParameter("dateStart", request.getDateStart());
        query.setParameter("dateStop", request.getDateStop());
        query.setParameter("searchRequest", request.getTitle());
        return query.getResultList();
    }
}
