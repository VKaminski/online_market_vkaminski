package com.gmail.kaminski.viktar.onlinemarket.service.converter;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Review;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ReviewNewDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ReviewDTO;

public interface ReviewConverter {
    Review toReview(ReviewDTO reviewDTO);

    Review toReview(ReviewNewDTO reviewDTO);

    ReviewDTO toReviewDTO(Review review);
}
