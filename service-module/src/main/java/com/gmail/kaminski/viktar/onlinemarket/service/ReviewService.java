package com.gmail.kaminski.viktar.onlinemarket.service;

import com.gmail.kaminski.viktar.onlinemarket.service.model.PageDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ReviewDTO;

public interface ReviewService {
    PageDTO<ReviewDTO> getReviewsPage(PageDTO<ReviewDTO> pageDTO);

    Integer getTotalAmount();

    void show(Long id);

    void delete(Long id);
}
