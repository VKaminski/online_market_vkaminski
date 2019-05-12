package com.gmail.kaminski.viktar.onlinemarket.service.converter.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.Review;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.ReviewConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.UserConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ReviewDTO;
import org.springframework.stereotype.Component;

@Component
public class ReviewConverterImpl implements ReviewConverter {
    private UserConverter userConverter;

    public ReviewConverterImpl(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Review toReview(ReviewDTO reviewDTO) {
        Review review = new Review();
        if (reviewDTO.getId() != null) {
            review.setId(reviewDTO.getId());
        }
        if (reviewDTO.getUserDTO() != null) {
            review.setUser(userConverter.toUser(reviewDTO.getUserDTO()));
        }
        if (reviewDTO.getContent() != null) {
            review.setContent(reviewDTO.getContent());
        }
        if (reviewDTO.getDate() != null) {
            review.setDate(reviewDTO.getDate());
        }
        review.setVisible(reviewDTO.getVisible());
        return review;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public ReviewDTO toReviewDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        if (review.getId() != null) {
            reviewDTO.setId(review.getId());
        }
        if (review.getUser() != null) {
            reviewDTO.setUserDTO(userConverter.toUserDTO(review.getUser()));
        }
        if (review.getContent() != null) {
            reviewDTO.setContent(review.getContent());
        }
        if (review.getDate() != null) {
            reviewDTO.setDate(review.getDate());
        }
        reviewDTO.setVisible(review.getVisible());
        return reviewDTO;
    }
}
