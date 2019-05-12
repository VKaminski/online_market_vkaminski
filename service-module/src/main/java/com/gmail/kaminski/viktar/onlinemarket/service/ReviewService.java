package com.gmail.kaminski.viktar.onlinemarket.service;

import com.gmail.kaminski.viktar.onlinemarket.service.model.ReviewDTO;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> get(Long firstElement, Integer amountElement);

    Long size();

    void show(Long id);

    void delete(Long id);
}
