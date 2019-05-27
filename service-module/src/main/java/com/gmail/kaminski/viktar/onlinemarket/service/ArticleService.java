package com.gmail.kaminski.viktar.onlinemarket.service;

import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.CommentDTO;
<<<<<<< HEAD
import com.gmail.kaminski.viktar.onlinemarket.service.model.NewArticleDTO;

import java.util.Date;
import java.util.List;

public interface ArticleService {
    List<ArticleDTO> getArticles(Integer firstElement, Integer amountElement);
=======

import java.util.List;

public interface ArticleService {
    List<ArticleDTO> getArticles(Long firstElement, Integer amountElement);
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f

    Long getAmountArticles();

    ArticleDTO getById(Long id);

<<<<<<< HEAD
    List<ArticleDTO> findByTitle(String searchRequest, Integer firstElement, Integer amountElement);

    List<ArticleDTO> findByDate(Date dateStart, Date dateStop, Integer firstElement, Integer amountElement);

    void add(NewArticleDTO newArticleDTO);

    void delete(Long id);

    void update(ArticleDTO articleDTO);
=======
    boolean addComment(CommentDTO commentDTO);

    List<ArticleDTO> findByTitle(String searchRequest, Long firstElement, Integer amountElement);

    List<ArticleDTO> findByDate(String dateStart, String dateStop, Long firstElement, Integer amountElement);

    void add(ArticleDTO articleDTO);

    void delete(Long id);
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f
}
