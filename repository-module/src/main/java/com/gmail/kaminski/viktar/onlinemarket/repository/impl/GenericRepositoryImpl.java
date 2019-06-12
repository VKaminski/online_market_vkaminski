package com.gmail.kaminski.viktar.onlinemarket.repository.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.GenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class GenericRepositoryImpl<I, T> implements GenericRepository<I, T> {

    protected Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public GenericRepositoryImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[1];
    }


    @Override
    public T findById(I id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public void add(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(T entity) {

        entityManager.merge(entity);
    }

    @Override
    public void remove(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public List<T> findAll(int firstElement, int amountElement) {
        String query = "from " + entityClass.getName() + " E where E.deleted = false";
        Query q = entityManager.createQuery(query)
                .setFirstResult(firstElement)
                .setMaxResults(amountElement);
        return q.getResultList();
    }

    @Override
    public Integer getAmountOfEntities() {
        String query = "SELECT COUNT(*) FROM " + entityClass.getName() + " E where E.deleted = false";
        Query q = entityManager.createQuery(query);
        return ((Number) q.getSingleResult()).intValue();
    }
}
