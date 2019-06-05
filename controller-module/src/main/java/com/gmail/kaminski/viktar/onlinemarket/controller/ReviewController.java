package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.controller.config.GlobalValue;
import com.gmail.kaminski.viktar.onlinemarket.controller.exception.WebControllerException;
import com.gmail.kaminski.viktar.onlinemarket.controller.util.RequestParamService;
import com.gmail.kaminski.viktar.onlinemarket.service.ReviewService;
import com.gmail.kaminski.viktar.onlinemarket.service.model.AppUserPrincipal;
import com.gmail.kaminski.viktar.onlinemarket.service.model.PageDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ReviewDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ReviewNewDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserAuthorizedDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class ReviewController {
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");
    private GlobalValue globalValue;
    private ReviewService reviewService;
    private RequestParamService requestParamService;

    public ReviewController(GlobalValue globalValue,
                            ReviewService reviewService,
                            RequestParamService requestParamService) {
        this.globalValue = globalValue;
        this.reviewService = reviewService;
        this.requestParamService = requestParamService;
    }

    @GetMapping("/reviews")
    public String reviews(
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "amountElement", required = false, defaultValue = "10") String amountElement,
            Model model) {
        logger.debug(custom, "page: " + page + " amountElement: " + amountElement);
        PageDTO<ReviewDTO> reviewsPage = new PageDTO<>();
        reviewsPage.setPage(requestParamService.getInteger(page, Integer.MAX_VALUE, globalValue.getDefaultPage()));
        reviewsPage.setAmountElementsOnPage(
                requestParamService.getInteger(
                        amountElement,
                        globalValue.getDefaultMaxAmountElements(),
                        globalValue.getDefaultAmountElements()));
        try {
            reviewService.getReviewsPage(reviewsPage);
            model.addAttribute("reviewsPage", reviewsPage);
            return "reviews";
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Reviews were not found!", e);
        }
    }

    @PostMapping("/reviews/{id}/hide")
    public String makeVisibleReview(@PathVariable("id") Long id) {
        try {
            reviewService.show(id);
            return "redirect:/reviews";
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Review was not found!", e);
        }
    }

    @PostMapping("/reviews/{id}/delete")
    public String deleteReview(@PathVariable("id") Long id) {
        try {
            reviewService.delete(id);
            return "redirect:/reviews";
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Review was not found!", e);
        }
    }

    @GetMapping("/reviews/new")
    public String showNewReviewPage() {
        return "newreview";
    }

    @PostMapping("/reviews/new")
    public String createNewReviewPage(@RequestParam("review") String review) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUserPrincipal userPrincipal = (AppUserPrincipal) auth.getPrincipal();
        UserAuthorizedDTO authorizedUser = userPrincipal.getAuthorizedUserDTO();
        ReviewNewDTO reviewDTO = new ReviewNewDTO();
        reviewDTO.setAuthorId(authorizedUser.getId());
        reviewDTO.setContent(review);
        reviewDTO.setDate(new Date(globalValue.getDefaultDate()));
        try {
            reviewService.add(reviewDTO);
            return "newreview";
        } catch (Exception e) {
            throw new WebControllerException("Please, correct your request! Review was not added!", e);
        }
    }

}
