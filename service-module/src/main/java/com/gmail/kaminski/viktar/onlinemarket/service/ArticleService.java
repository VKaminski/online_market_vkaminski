package com.gmail.kaminski.viktar.onlinemarket.service;

import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.CommentDTO;

import java.util.List;

public interface ArticleService {
    List<ArticleDTO> getArticles(Long firstElement, Integer amountElement);

    Long getAmountArticles();

    ArticleDTO getById(Long id);

    boolean addComment(CommentDTO commentDTO);

    List<ArticleDTO> findByTitle(String searchRequest, Long firstElement, Integer amountElement);

    List<ArticleDTO> findByDate(String dateStart, String dateStop, Long firstElement, Integer amountElement);

    void add(ArticleDTO articleDTO);

    void delete(Long id);
}
