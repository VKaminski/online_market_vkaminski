package com.gmail.kaminski.viktar.onlinemarket.service;

import com.gmail.kaminski.viktar.onlinemarket.service.model.ReviewDTO;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> get(Integer firstElement, Integer amountElement);

    Long getTotalAmount();

    void show(Long id);

    void delete(Long id);
}
