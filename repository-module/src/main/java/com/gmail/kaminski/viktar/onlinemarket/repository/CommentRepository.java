package com.gmail.kaminski.viktar.onlinemarket.repository;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.Comment;

<<<<<<< HEAD
public interface CommentRepository extends GenericRepository<Long, Comment> {
=======
import java.sql.Connection;
import java.util.List;

public interface CommentRepository extends GenericRepository {
    List<Comment> getCommentsForArticleById(Connection connection, Long articleId);

    void add(Connection connection, Comment comment);
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f
}
