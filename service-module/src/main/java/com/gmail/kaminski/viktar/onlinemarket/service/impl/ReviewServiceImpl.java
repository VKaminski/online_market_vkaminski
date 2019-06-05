package com.gmail.kaminski.viktar.onlinemarket.service.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.ReviewRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Review;
import com.gmail.kaminski.viktar.onlinemarket.service.ReviewService;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.ReviewConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.PageDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ReviewDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ReviewNewDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.validator.PageValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private ReviewConverter reviewConverter;
    private PageValidator pageValidator;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             ReviewConverter reviewConverter,
                             PageValidator pageValidator) {
        this.reviewRepository = reviewRepository;
        this.reviewConverter = reviewConverter;
        this.pageValidator = pageValidator;
    }

    @Override
    @Transactional
    public PageDTO<ReviewDTO> getReviewsPage(PageDTO<ReviewDTO> pageDTO) {
        pageDTO.setAmountElements(reviewRepository.getAmountOfEntities());
        pageValidator.valid(pageDTO);
        int firstElement = (pageDTO.getPage() - 1) * pageDTO.getAmountElementsOnPage();
        int amountElementsOnPage = pageDTO.getAmountElementsOnPage();
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
        reviewRepository.remove(review);
    }

    @Override
    @Transactional
    public void add(ReviewNewDTO reviewDTO) {
        Review review = reviewConverter.toReview(reviewDTO);
        reviewRepository.add(review);
    }
}
