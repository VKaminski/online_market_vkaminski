package com.gmail.kaminski.viktar.onlinemarket.repository;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.Comment;

import java.sql.Connection;
import java.util.List;

public interface CommentRepository extends GenericRepository {
    List<Comment> getCommentsForArticleById(Connection connection, Long articleId);

    void add(Connection connection, Comment comment);
}
