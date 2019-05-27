package com.gmail.kaminski.viktar.onlinemarket.service;

import com.gmail.kaminski.viktar.onlinemarket.service.model.CommentDTO;
import org.springframework.transaction.annotation.Transactional;

public interface CommentService {
    @Transactional
    boolean addComment(CommentDTO commentDTO);

    @Transactional
    void deleteComment(Long commentId);
}
