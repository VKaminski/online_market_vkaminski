package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.controller.util.RequestParamService;
import com.gmail.kaminski.viktar.onlinemarket.service.ReviewService;
import com.gmail.kaminski.viktar.onlinemarket.service.model.PageDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ReviewDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReviewController {
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");
    private ReviewService reviewService;
    private RequestParamService requestParamService;

    public ReviewController(ReviewService reviewService,
                            RequestParamService requestParamService) {
        this.reviewService = reviewService;
        this.requestParamService = requestParamService;
    }

    @GetMapping("/reviews")
    public String reviews(@RequestParam(value = "page", required = false, defaultValue = "1") String page,
                          @RequestParam(value = "amountElement", required = false, defaultValue = "10") String amountElement,
                          Model model) {
        logger.debug(custom, "page: " + page + " amountElement: " + amountElement);
        PageDTO<ReviewDTO> reviewsPage = new PageDTO<>();
        reviewsPage.setPage(requestParamService.getElements(page, Integer.MAX_VALUE, 1));
        reviewsPage.setAmountElementsOnPage(requestParamService.getElements(amountElement, 100, 10));
        reviewService.getReviewsPage(reviewsPage);
        model.addAttribute("reviewsPage", reviewsPage);
        return "reviews";
    }

    @PostMapping("/reviews/{id}/hide")
    public String makeVisibleReview(@PathVariable("id") Long id) {
        reviewService.show(id);
        return "redirect:/reviews";
    }

    @PostMapping("/reviews/{id}/delete")
    public String deleteReview(@PathVariable("id") Long id) {
        reviewService.delete(id);
        return "redirect:/reviews";
    }

}
