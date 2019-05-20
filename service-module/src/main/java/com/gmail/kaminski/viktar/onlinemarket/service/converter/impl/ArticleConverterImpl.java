package com.gmail.kaminski.viktar.onlinemarket.service.converter.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.Article;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Comment;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.ArticleConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.CommentConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.UserConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.CommentDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArticleConverterImpl implements ArticleConverter {
    private UserConverter userConverter;
    private CommentConverter commentConverter;

    public ArticleConverterImpl(UserConverter userConverter, CommentConverter commentConverter) {
        this.userConverter = userConverter;
        this.commentConverter = commentConverter;
    }

    @Override
    public ArticleDTO toArticleDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        if (article.getId() != null) {
            articleDTO.setId(article.getId());
        }
        if (article.getAuthor() != null) {
            articleDTO.setAuthor(userConverter.toUserDTO(article.getAuthor()));
        }
        if (article.getTitle() != null){
            articleDTO.setTitle(article.getTitle());
        }
        if (article.getContent() != null) {
            articleDTO.setContent(article.getContent());
        }
        if (article.getDate() != null) {
            articleDTO.setDate(article.getDate());
        }
        if (article.getAmountComments() != null) {
            articleDTO.setAmountComments(article.getAmountComments());
        }

        if (article.getComments() != null) {
            if (!article.getComments().isEmpty()){
                List<CommentDTO> commentDTOs = new ArrayList<>();
                for (Comment comment : article.getComments()) {
                    commentDTOs.add(commentConverter.toCommentDTO(comment));
                }
                articleDTO.setComments(commentDTOs);
            }
        }
        return articleDTO;
    }
}
