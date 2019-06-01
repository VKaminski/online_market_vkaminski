package com.gmail.kaminski.viktar.onlinemarket.service;

import com.gmail.kaminski.viktar.onlinemarket.service.model.CommentDTO;

public interface CommentService {
    boolean addComment(CommentDTO commentDTO);

    void deleteComment(Long commentId);
}
