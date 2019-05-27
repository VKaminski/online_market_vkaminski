package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.controller.model.Paginator;
import com.gmail.kaminski.viktar.onlinemarket.controller.util.PaginatorService;
import com.gmail.kaminski.viktar.onlinemarket.service.ReviewService;
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

import java.util.List;

@Controller
public class ReviewController {
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");
    private ReviewService reviewService;
    private PaginatorService paginatorService;

    protected ReviewController(PaginatorService paginatorService,
                               ReviewService reviewService) {
        this.paginatorService = paginatorService;
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews")
    public String reviews(@RequestParam(value = "page", required = false, defaultValue = "1") String page,
                          @RequestParam(value = "amountElement", required = false, defaultValue = "10") String amountElement,
                          Model model) {
        Long sizeList = reviewService.getTotalAmount();
        Paginator paginator = paginatorService.get(page, amountElement, sizeList);
        Integer firstElement = (paginator.getPage() - 1) * paginator.getAmountElementOnPage();
        List<ReviewDTO> reviews = reviewService.get(firstElement, paginator.getAmountElementOnPage());
        Long id = new Long(0);
        model.addAttribute("reviewId", id);
        model.addAttribute("reviews", reviews);
        model.addAttribute("paginator", paginator);
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
