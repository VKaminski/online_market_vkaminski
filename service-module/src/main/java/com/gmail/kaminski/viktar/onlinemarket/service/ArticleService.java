package com.gmail.kaminski.viktar.onlinemarket.service;

import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.CommentDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.NewArticleDTO;

import java.util.Date;
import java.util.List;

public interface ArticleService {
    List<ArticleDTO> getArticles(Integer firstElement, Integer amountElement);

    Long getAmountArticles();

    ArticleDTO getById(Long id);

    boolean addComment(CommentDTO commentDTO);

    List<ArticleDTO> findByTitle(String searchRequest, Integer firstElement, Integer amountElement);

    List<ArticleDTO> findByDate(Date dateStart, Date dateStop, Integer firstElement, Integer amountElement);

    void add(NewArticleDTO newArticleDTO);

    void delete(Long id);

    void update(ArticleDTO articleDTO);

    void deleteComment(Long commentId);
}
