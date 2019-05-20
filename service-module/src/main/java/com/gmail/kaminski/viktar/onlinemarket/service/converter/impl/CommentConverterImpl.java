package com.gmail.kaminski.viktar.onlinemarket.service.converter.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.Comment;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.CommentConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.CommentDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentConverterImpl implements CommentConverter {
    private UserConverterImpl userConverter;

    public CommentConverterImpl(UserConverterImpl userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public CommentDTO toCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        if (comment.getArticleId() != null) {
            commentDTO.setArticleId(comment.getArticleId());
        }
        if (comment.getId() != null) {
            commentDTO.setId(comment.getId());
        }
        if (comment.getAuthor() != null) {
            commentDTO.setAuthor(userConverter.toUserDTO(comment.getAuthor()));
        }
        if (comment.getContent() != null) {
            commentDTO.setContent(comment.getContent());
        }
        if (comment.getDate() != null) {
            commentDTO.setDate(comment.getDate());
        }
        if (comment.getHead() != null) {
            commentDTO.setHead(comment.getHead());
        }
        return commentDTO;
    }

    @Override
    public Comment toComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        if (commentDTO.getArticleId() != null) {
            comment.setArticleId(commentDTO.getArticleId());
        }
        if (commentDTO.getId() != null) {
            comment.setId(commentDTO.getId());
        }
        if (commentDTO.getAuthor() != null) {
            comment.setAuthor(userConverter.toUser(commentDTO.getAuthor()));
        }
        if (commentDTO.getContent() != null) {
            comment.setContent(commentDTO.getContent());
        }
        if (commentDTO.getDate() != null) {
            comment.setDate(commentDTO.getDate());
        }
        if (commentDTO.getHead() != null) {
            comment.setHead(commentDTO.getHead());
        }
        return comment;
    }
}
