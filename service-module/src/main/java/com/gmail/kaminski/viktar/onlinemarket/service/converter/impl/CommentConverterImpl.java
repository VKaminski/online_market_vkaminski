package com.gmail.kaminski.viktar.onlinemarket.service.converter.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Article;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Comment;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.CommentConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.UserConverter;
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
        commentDTO.setArticleId(comment.getArticle().getId());
        commentDTO.setAuthor(userConverter.toUserDTO(comment.getAuthor()));
        commentDTO.setContent(comment.getContent());
        if (comment.getDate() != null) {
            commentDTO.setDate(comment.getDate());
        }
        if (comment.getHeadComment() != null) {
            commentDTO.setHeadCommentId(comment.getHeadComment().getId());
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
        article.setId(commentDTO.getArticleId());
        comment.setArticle(article);
        comment.setAuthor(userConverter.toUser(commentDTO.getAuthor()));
        comment.setContent(commentDTO.getContent());
        if (commentDTO.getDate() != null) {
            comment.setDate(commentDTO.getDate());
        }
        if (commentDTO.getHeadCommentId() != null) {
            Comment headComment = new Comment();
            headComment.setId(commentDTO.getHeadCommentId());
            comment.setHeadComment(headComment);
        }
        return comment;
    }
}
