package com.gmail.kaminski.viktar.onlinemarket.repository.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.ReviewRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.exception.ReviewRepositoryException;
import com.gmail.kaminski.viktar.onlinemarket.repository.exception.UserRepositoryException;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Review;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ReviewRepositoryImpl extends GenericRepositoryImpl implements ReviewRepository {
    private static final Logger logger = LoggerFactory.getLogger(ReviewRepositoryImpl.class);

    @Override
    public List<Review> get(Connection connection, Long firstElement, Integer amountElement) {
        String sqlRequest = "SELECT R.id, R.content, R.date, R.visible, U.surname, U.name, U.patronymic" +
                " FROM Review AS R JOIN User AS U ON R.user_id = U.id" +
                " WHERE R.deleted = false" +
                " ORDER BY R.date" +
                " LIMIT ?,?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setLong(1, firstElement);
            preparedStatement.setInt(2, amountElement);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Review> reviews = new ArrayList<>();
                while (resultSet.next()) {
                    reviews.add(get(resultSet));
                }
                return reviews;
            } catch (SQLException e) {
                logger.error(this.getClass().getName() + " problem with ResultSet in get (list)!");
                throw new ReviewRepositoryException(e);
            }
        } catch (SQLException e) {
            logger.error(this.getClass().getName() + " problem with prepareStatement in get (list)!");
            throw new ReviewRepositoryException(e);
        }
    }

    @Override
    public Long size(Connection connection) {
        String sqlRequest = "SELECT COUNT(*) FROM Review WHERE deleted = false ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long size = resultSet.getLong(1);
                if (!resultSet.next()) {
                    return size;
                } else {
                    String message = this.getClass().getName() + " more than 1 result";
                    logger.error(message);
                    throw new ReviewRepositoryException(message);
                }
            } else {
                String message = this.getClass().getName() + " less than 1 result";
                logger.error(message);
                throw new ReviewRepositoryException(message);
            }
        } catch (SQLException e) {
            logger.error(this.getClass().getName() + " problem with PrepareStatement in size!");
            throw new ReviewRepositoryException(e);
        }
    }

    @Override
    public void show(Connection connection, Long id) {
        String sqlRequest = "UPDATE Review SET visible = true WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setLong(1, id);
            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows == 0) {
                String message = this.getClass().getName() + " Review hasn't been show!";
                logger.error(message);
                throw new UserRepositoryException(message);
            }
        } catch (SQLException e) {
            logger.error(this.getClass().getName() + "SQLException review show");
            throw new UserRepositoryException(e);
        }
    }

    @Override
    public void delete(Connection connection, Long id) {
        String sqlRequest = "UPDATE Review SET deleted = true WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setLong(1, id);
            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows == 0) {
                String message = this.getClass().getName() + " Review hasn't been delete!";
                logger.error(message);
                throw new UserRepositoryException(message);
            }
        } catch (SQLException e) {
            logger.error(this.getClass().getName() + "SQLException review delete");
            throw new UserRepositoryException(e);
        }
    }

    @SuppressWarnings("Duplicates")
    private Review get(ResultSet resultSet) throws SQLException {
        Review review = new Review();
        try {
            Long id = resultSet.getLong("id");
            String surname = resultSet.getString("surname");
            String name = resultSet.getString("name");
            String patronymic = resultSet.getString("patronymic");
            String content = resultSet.getString("content");
            Date date = resultSet.getDate("date");
            Boolean visible = resultSet.getBoolean("visible");
            review.setId(id);
            User user = new User();
            user.setSurname(surname);
            user.setName(name);
            user.setPatronymic(patronymic);
            review.setUser(user);
            review.setContent(content);
            review.setDate(date);
            review.setVisible(visible);
            return review;
        } catch (SQLException e) {
            String errorMessage = "Exception: ReviewRepository private get";
            logger.error(errorMessage);
            throw new SQLException(errorMessage, e);
        }
    }
}
