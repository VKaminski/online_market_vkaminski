package com.gmail.kaminski.viktar.onlinemarket.repository.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.CommentRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.exception.CommentRepositoryException;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Comment;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class CommentRepositoryImpl extends GenericRepositoryImpl implements CommentRepository {
    private static final Logger logger = LoggerFactory.getLogger(CommentRepositoryImpl.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");

    @Override
    public List<Comment> getCommentsForArticleById(Connection connection, Long articleId) {
        String sqlRequest = "SELECT" +
                " C.id AS commentId, C.content AS commentContent, C.date AS commentDate, C.head_comment_id AS commentHead" +
                ", U.id AS userId, U.name AS userName, U.surname AS userSurname" +
                " FROM Comment AS C" +
                " LEFT JOIN User AS U ON U.id = C.user_id" +
                " WHERE C.article_id = ?" +
                " ORDER BY C.date;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setLong(1, articleId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Comment> comments = new ArrayList<>();
                while (resultSet.next()) {
                    comments.add(get(resultSet));
                }
                return comments;
            } catch (SQLException e) {
                logger.debug(custom, this.getClass().getName() + " problem with ResultSet in getCommentsForArticleById!");
                throw new CommentRepositoryException(e);
            }
        } catch (SQLException e) {
            logger.debug(custom, this.getClass().getName() + " problem with prepareStatement in getCommentsForArticleById!");
            throw new CommentRepositoryException(e);
        }
    }

    @Override
    public void add(Connection connection, Comment comment) {
        String sqlRequest = "INSERT INTO Comment(article_id, user_id, content, head_comment_id) " +
                "VALUES (?,(SELECT id FROM User WHERE email = ?),?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, comment.getArticleId());
            preparedStatement.setString(2, comment.getAuthor().getEmail());
            preparedStatement.setString(3, comment.getContent());
            preparedStatement.setLong(4, comment.getHead());
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                resultSet.next();
            } catch (SQLException e) {
                logger.debug(custom, this.getClass().getName() + " problem with ResultSet in add!");
                throw new CommentRepositoryException(e);
            }
        } catch (SQLException e) {
            logger.debug(custom, this.getClass().getName() + " problem with PrepareStatement in add!");
            throw new CommentRepositoryException(e);
        }
    }

    private Comment get(ResultSet resultSet) throws SQLException {
        Comment comment = new Comment();
        try {
            Long userId = resultSet.getLong("userId");
            String userName = resultSet.getString("userName");
            String userSurname = resultSet.getString("userSurname");
            User user = new User();
            user.setId(userId);
            user.setName(userName);
            user.setSurname(userSurname);
            comment.setAuthor(user);
            Long commentId = resultSet.getLong("commentId");
            String commentContent = resultSet.getString("commentContent");
            Date commentDate = resultSet.getDate("commentDate");
            Long commentHead = resultSet.getLong("commentHead");
            comment.setId(commentId);
            comment.setContent(commentContent);
            comment.setDate(commentDate);
            comment.setHead(commentHead);
            return comment;
        } catch (SQLException e) {
            String errorMessage = "Exception: CommentRepository private get";
            logger.debug(custom, errorMessage);
            throw new SQLException(errorMessage, e);
        }
    }
}
