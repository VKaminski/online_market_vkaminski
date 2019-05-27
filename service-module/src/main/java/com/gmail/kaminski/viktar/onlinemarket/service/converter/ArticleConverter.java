package com.gmail.kaminski.viktar.onlinemarket.service.converter;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.Article;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
<<<<<<< HEAD
import com.gmail.kaminski.viktar.onlinemarket.service.model.NewArticleDTO;

import java.text.ParseException;
=======
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f

public interface ArticleConverter {
    ArticleDTO toArticleDTO(Article article);

    Article toArticle(ArticleDTO articleDTO);
<<<<<<< HEAD

    Article toArticle(NewArticleDTO newArticleDTO);
=======
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f
}
