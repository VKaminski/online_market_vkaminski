package com.gmail.kaminski.viktar.onlinemarket.service.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.ReviewRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.exception.ReviewRepositoryException;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Review;
import com.gmail.kaminski.viktar.onlinemarket.service.ReviewService;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.ReviewConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.exception.ReviewServiceException;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ReviewDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);
    private ReviewRepository reviewRepository;
    private ReviewConverter reviewConverter;

    private ReviewServiceImpl(ReviewRepository reviewRepository, ReviewConverter reviewConverter) {
        this.reviewRepository = reviewRepository;
        this.reviewConverter = reviewConverter;
    }

    @Override
    public List<ReviewDTO> get(Long firstElement, Integer amountElement) {
        try (Connection connection = reviewRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Review> reviews = reviewRepository.get(connection, firstElement, amountElement);
                List<ReviewDTO> output = new ArrayList<>();
                for (Review review : reviews) {
                    output.add(reviewConverter.toReviewDTO(review));
                }
                connection.commit();
                return output;
            } catch (ReviewRepositoryException e) {
                logger.error(this.getClass().getName() + "rollback operation in get (list)");
                connection.rollback();
                throw new ReviewServiceException(e);
            }
        } catch (SQLException e) {
            logger.error(this.getClass().getName() + "problem with connection in get (list)");
            throw new ReviewServiceException(e);
        }
    }

    @Override
    public Long size() {
        try (Connection connection = reviewRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Long size = reviewRepository.size(connection);
                connection.commit();
                return size;
            } catch (ReviewRepositoryException e) {
                logger.error(this.getClass().getName() + "rollback operation in size!");
                connection.rollback();
                throw new ReviewServiceException(e);
            }
        } catch (SQLException e) {
            logger.error(this.getClass().getName() + "problem with connection in size!");
            throw new ReviewServiceException(e);
        }
    }

    @Override
    public void show(Long id) {
        try (Connection connection = reviewRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                reviewRepository.show(connection, id);
                connection.commit();
            } catch (ReviewRepositoryException e) {
                logger.error(this.getClass().getName() + "rollback operation in show!");
                connection.rollback();
                throw new ReviewServiceException(e);
            }
        } catch (SQLException e) {
            logger.error(this.getClass().getName() + "problem with connection in show!");
            throw new ReviewServiceException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = reviewRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                reviewRepository.delete(connection, id);
                connection.commit();
            } catch (ReviewRepositoryException e) {
                logger.error(this.getClass().getName() + "rollback operation in delete!");
                connection.rollback();
                throw new ReviewServiceException(e);
            }
        } catch (SQLException e) {
            logger.error(this.getClass().getName() + "problem with connection in delete!");
            throw new ReviewServiceException(e);
        }
    }
}
