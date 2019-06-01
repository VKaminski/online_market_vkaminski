package com.gmail.kaminski.viktar.onlinemarket.service.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.ReviewRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Review;
import com.gmail.kaminski.viktar.onlinemarket.service.ReviewService;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.ReviewConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.PageDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ReviewDTO;
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
    public PageDTO<ReviewDTO> getReviewsPage(PageDTO<ReviewDTO> pageDTO) {
        int amountElements = reviewRepository.getAmountOfEntities();
        pageDTO.setAmountElements(amountElements);
        Integer amountElementsOnPage = pageDTO.getAmountElementsOnPage();
        int amountPages = amountElements / amountElementsOnPage + 1;
        if (pageDTO.getPage() > (amountPages)) {
            pageDTO.setPage(amountPages);
        }
        int firstElement = (pageDTO.getPage() - 1) * pageDTO.getAmountElementsOnPage();
        List<Review> reviews = reviewRepository.findAll(firstElement, amountElementsOnPage);
        List<ReviewDTO> reviewDTOs = new ArrayList<>();
        for (Review review : reviews) {
            reviewDTOs.add(reviewConverter.toReviewDTO(review));
        }
        pageDTO.setElements(reviewDTOs);
        return pageDTO;
    }

    @Override
    @Transactional
    public Integer getTotalAmount() {
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
