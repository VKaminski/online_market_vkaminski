package com.gmail.kaminski.viktar.onlinemarket.service.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.ReviewRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Review;
import com.gmail.kaminski.viktar.onlinemarket.service.ReviewService;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.ReviewConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ReviewDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private ReviewConverter reviewConverter;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewConverter reviewConverter) {
        this.reviewRepository = reviewRepository;
        this.reviewConverter = reviewConverter;
    }

    @Override
    @Transactional
    public List<ReviewDTO> get(Integer firstElement, Integer amountElement) {
        List<Review> reviews = reviewRepository.findAll(firstElement, amountElement);
        List<ReviewDTO> output = new ArrayList<>();
        for (Review review : reviews) {
            output.add(reviewConverter.toReviewDTO(review));
        }
        return output;
    }

    @Override
    @Transactional
    public Long getTotalAmount() {
        return reviewRepository.getAmountOfEntities();
    }

    @Override
    @Transactional
    public void show(Long id) {
        Review review = reviewRepository.findById(id);
        if (review.getVisible()) {
            review.setVisible(false);
        } else {
            review.setVisible(true);
        }
        reviewRepository.update(review);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Review review = reviewRepository.findById(id);
        review.setUser(null);
        reviewRepository.remove(review);
    }
}
