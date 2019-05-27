package com.gmail.kaminski.viktar.onlinemarket.service.converter.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.Article;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Comment;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.ArticleConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.CommentConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.UserConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.CommentDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentConverterImpl implements CommentConverter {
    private UserConverter userConverter;

    public CommentConverterImpl(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public CommentDTO toCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        if (comment.getId() != null) {
            commentDTO.setId(comment.getId());
        }
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(comment.getArticle().getId());
        commentDTO.setArticle(articleDTO);
        commentDTO.setAuthor(userConverter.toUserDTO(comment.getAuthor()));
        commentDTO.setContent(comment.getContent());
        if (comment.getDate() != null) {
            commentDTO.setDate(comment.getDate());
        }
        if (comment.getHeadComment() != null) {
            CommentDTO headCommentDTO = new CommentDTO();
            headCommentDTO.setId(comment.getHeadComment().getId());
            commentDTO.setHeadComment(headCommentDTO);
            commentDTO.setHeadComment(headCommentDTO);
        }
        return commentDTO;
    }

    @Override
    public Comment toComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        if (commentDTO.getId() != null) {
            comment.setId(commentDTO.getId());
        }
        Article article = new Article();
        article.setId(commentDTO.getArticle().getId());
        comment.setArticle(article);
        comment.setAuthor(userConverter.toUser(commentDTO.getAuthor()));
        comment.setContent(commentDTO.getContent());
        if (commentDTO.getDate() != null) {
            comment.setDate(commentDTO.getDate());
        }
        if (commentDTO.getHeadComment() != null) {
            Comment headComment = new Comment();
            headComment.setId(commentDTO.getHeadComment().getId());
            comment.setHeadComment(headComment);
        }
        return comment;
    }
}
