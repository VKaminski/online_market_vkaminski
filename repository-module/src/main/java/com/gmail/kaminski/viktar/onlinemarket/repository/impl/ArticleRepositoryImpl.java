package com.gmail.kaminski.viktar.onlinemarket.repository.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.ArticleRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.exception.ArticleRepositoryException;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Article;
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
public class ArticleRepositoryImpl extends GenericRepositoryImpl implements ArticleRepository {
    private static final Logger logger = LoggerFactory.getLogger(ArticleRepositoryImpl.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");

    @Override
    public Long getAmountArticles(Connection connection) {
        String sqlRequest = "SELECT COUNT(*) FROM Article WHERE deleted = false";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long amount = resultSet.getLong(1);
                if (!resultSet.next()) {
                    return amount;
                } else {
                    String message = this.getClass().getName() + " more than 1 result";
                    logger.debug(custom, message);
                    throw new ArticleRepositoryException(message);
                }
            } else {
                String message = this.getClass().getName() + " less than 1 result";
                logger.debug(custom, message);
                throw new ArticleRepositoryException(message);
            }
        } catch (SQLException e) {
            logger.debug(this.getClass().getName() + " problem with PrepareStatement in getAmountArticles!");
            throw new ArticleRepositoryException(e);
        }
    }

    @Override
    public Article getById(Connection connection, Long id) {
        String sqlRequest = "SELECT U.id AS authorId, U.surname AS authorSurname,U.name AS authorName" +
                ", A.id AS articleId, A.title AS articleTitle, A.content  AS articleContent, A.date  AS articleDate" +
                ", COUNT(C.id) AS amountComments " +
                "FROM Article AS A " +
                "JOIN User AS U ON U.id = A.user_id " +
                "LEFT JOIN Comment AS C ON A.id = C.article_id " +
                "WHERE A.id = ? AND A.deleted = false " +
                "GROUP BY A.id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return get(resultSet);
            } catch (SQLException e) {
                logger.debug(custom, this.getClass().getName() + " problem with ResultSet in getById!");
                throw new ArticleRepositoryException(e);
            }
        } catch (SQLException e) {
            logger.debug(custom, this.getClass().getName() + " problem with prepareStatement in getById!");
            throw new ArticleRepositoryException(e);
        }
    }

    @Override
    public List<Article> getArticles(Connection connection, Long firstElement, Integer amountElement) {
        String sqlRequest = "SELECT U.id AS authorId, U.surname AS authorSurname,U.name AS authorName" +
                ", A.id AS articleId, A.title AS articleTitle, A.content  AS articleContent, A.date  AS articleDate" +
                ", COUNT(C.id) AS amountComments " +
                "FROM Article AS A " +
                "JOIN User AS U ON U.id = A.user_id " +
                "LEFT JOIN Comment AS C ON A.id = C.article_id " +
                "WHERE A.deleted = false " +
                "GROUP BY A.id " +
                "ORDER BY A.date " +
                "LIMIT ?, ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setLong(1, firstElement);
            preparedStatement.setInt(2, amountElement);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Article> articles = new ArrayList<>();
                while (resultSet.next()) {
                    articles.add(get(resultSet));
                }
                return articles;
            } catch (SQLException e) {
                logger.debug(custom, this.getClass().getName() + " problem with ResultSet in getArticles!");
                throw new ArticleRepositoryException(e);
            }
        } catch (SQLException e) {
            logger.debug(custom, this.getClass().getName() + " problem with prepareStatement in getArticles!");
            throw new ArticleRepositoryException(e);
        }
    }

    @Override
    public List<Article> findByTitle(Connection connection, String searchRequest, Long firstElement, Integer amountElement) {
        String sqlRequest = "SELECT U.id AS authorId, U.surname AS authorSurname,U.name AS authorName" +
                ", A.id AS articleId, A.title AS articleTitle, A.content  AS articleContent, A.date  AS articleDate" +
                ", COUNT(C.id) AS amountComments " +
                "FROM Article AS A " +
                "JOIN User AS U ON U.id = A.user_id " +
                "LEFT JOIN Comment AS C ON A.id = C.article_id " +
                "WHERE A.title REGEXP ? AND A.deleted = false " +
                "GROUP BY A.id " +
                "ORDER BY A.date " +
                "LIMIT ?, ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setString(1, searchRequest);
            preparedStatement.setLong(2, firstElement);
            preparedStatement.setInt(3, amountElement);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Article> articles = new ArrayList<>();
                while (resultSet.next()) {
                    articles.add(get(resultSet));
                }
                return articles;
            } catch (SQLException e) {
                logger.debug(custom, this.getClass().getName() + " problem with ResultSet in findByTitle!");
                throw new ArticleRepositoryException(e);
            }
        } catch (SQLException e) {
            logger.debug(custom, this.getClass().getName() + " problem with prepareStatement in findByTitle!");
            throw new ArticleRepositoryException(e);
        }
    }

    @Override
    public List<Article> findByDate(Connection connection, String dateStart, String dateStop, Long firstElement, Integer amountElement) {
        String sqlRequest = "SELECT U.id AS authorId, U.surname AS authorSurname,U.name AS authorName" +
                ", A.id AS articleId, A.title AS articleTitle, A.content  AS articleContent, A.date  AS articleDate" +
                ", COUNT(C.id) AS amountComments " +
                "FROM Article AS A " +
                "JOIN User AS U ON U.id = A.user_id " +
                "LEFT JOIN Comment AS C ON A.id = C.article_id " +
                "WHERE A.deleted = false AND (A.date BETWEEN ? AND ?) " +
                "GROUP BY A.id " +
                "ORDER BY A.date " +
                "LIMIT ?, ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setString(1, dateStart);
            preparedStatement.setString(2, dateStop);
            preparedStatement.setLong(3, firstElement);
            preparedStatement.setInt(4, amountElement);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Article> articles = new ArrayList<>();
                while (resultSet.next()) {
                    articles.add(get(resultSet));
                }
                return articles;
            } catch (SQLException e) {
                logger.debug(custom, this.getClass().getName() + " problem with ResultSet in findByTitle!");
                throw new ArticleRepositoryException(e);
            }
        } catch (SQLException e) {
            logger.debug(custom, this.getClass().getName() + " problem with prepareStatement in findByTitle!");
            throw new ArticleRepositoryException(e);
        }
    }

    @Override
    public void add(Connection connection, Article article) {
        String sqlRequest = "INSERT INTO Article(user_id, title, content) " +
                "VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, article.getAuthor().getId());
            preparedStatement.setString(2, article.getTitle());
            preparedStatement.setString(3, article.getContent());
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                resultSet.next();
            } catch (SQLException e) {
                logger.debug(custom, this.getClass().getName() + " problem with ResultSet in add!");
                throw new ArticleRepositoryException(e);
            }
        } catch (SQLException e) {
            logger.debug(custom, this.getClass().getName() + " problem with PrepareStatement in add!");
            throw new ArticleRepositoryException(e);
        }
    }

    @Override
    public void delete(Connection connection, Long id) {
        String sqlRequest = "UPDATE Article SET deleted = true WHERE id = ? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setLong(1, id);
            int deletedRows = preparedStatement.executeUpdate();
            if (deletedRows == 0) {
                String message = this.getClass().getName() + " Article hasn't been deleted!";
                logger.error(custom, message);
                throw new ArticleRepositoryException(message);
            }
        } catch (SQLException e) {
            logger.error(custom, this.getClass().getName() + "SQLException Article delete");
            throw new ArticleRepositoryException(e);
        }
    }

    private Article get(ResultSet resultSet) throws SQLException {
        Article article = new Article();
        try {
            Long articleId = resultSet.getLong("articleId");
            Long authorId = resultSet.getLong("authorId");
            String authorSurname = resultSet.getString("authorSurname");
            String authorName = resultSet.getString("authorName");
            User user = new User();
            user.setId(authorId);
            user.setSurname(authorSurname);
            user.setName(authorName);
            String title = resultSet.getString("articleTitle");
            String content = resultSet.getString("articleContent");
            Date date = resultSet.getDate("articleDate");
            Integer amountComments = resultSet.getInt("amountComments");
            article.setId(articleId);
            article.setAuthor(user);
            article.setTitle(title);
            article.setContent(content);
            article.setDate(date);
            article.setAmountComments(amountComments);
            return article;
        } catch (SQLException e) {
            String errorMessage = "Exception: ArticleRepository private get";
            logger.debug(custom, errorMessage);
            throw new SQLException(errorMessage, e);
        }
    }
}
