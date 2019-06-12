package com.gmail.kaminski.viktar.onlinemarket.repository.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.RoleRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class RoleRepositoryImpl extends GenericRepositoryImpl<Long, Role> implements RoleRepository {

    @Override
    public List<Role> findAll(int firstElement, int amountElement) {
        String query = "from " + entityClass.getName();
        Query q = entityManager.createQuery(query)
                .setFirstResult(firstElement)
                .setMaxResults(amountElement);
        return q.getResultList();
    }

    @Override
    public Integer getAmountOfEntities() {
        String query = "SELECT COUNT(*) FROM " + entityClass.getName();
        Query q = entityManager.createQuery(query);
        return ((Number) q.getSingleResult()).intValue();
    }
}
