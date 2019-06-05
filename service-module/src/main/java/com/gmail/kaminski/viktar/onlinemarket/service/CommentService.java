package com.gmail.kaminski.viktar.onlinemarket.service;

import com.gmail.kaminski.viktar.onlinemarket.service.model.CommentNewDTO;

public interface CommentService {
    boolean addComment(CommentNewDTO commentDTO);

    void deleteComment(Long commentId);
}
