package com.gmail.kaminski.viktar.onlinemarket.repository;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.Review;

import java.sql.Connection;
import java.util.List;

public interface ReviewRepository extends GenericRepository {
    List<Review> get(Connection connection, Long firstElement, Integer amountElement);

    Long size(Connection connection);

    void show(Connection connection, Long id);

    void delete(Connection connection, Long id);
}
