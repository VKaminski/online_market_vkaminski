package com.gmail.kaminski.viktar.onlinemarket.service.converter;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Comment;
import com.gmail.kaminski.viktar.onlinemarket.service.model.CommentDTO;

public interface CommentConverter {
    CommentDTO toCommentDTO(Comment comment);

    Comment toComment(CommentDTO commentDTO);
}
