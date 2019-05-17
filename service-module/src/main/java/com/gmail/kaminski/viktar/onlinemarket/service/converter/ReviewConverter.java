package com.gmail.kaminski.viktar.onlinemarket.service.converter;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.Review;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ReviewDTO;

public interface ReviewConverter {
    Review toReview(ReviewDTO reviewDTO);
    ReviewDTO toReviewDTO(Review review);
}
