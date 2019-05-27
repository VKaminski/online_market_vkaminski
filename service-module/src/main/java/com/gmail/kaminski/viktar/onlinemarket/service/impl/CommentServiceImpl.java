package com.gmail.kaminski.viktar.onlinemarket.service.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.CommentRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Comment;
import com.gmail.kaminski.viktar.onlinemarket.service.CommentService;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.CommentConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.CommentDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {
    @Value("${custom.comment.forbidden.word}")
    private String forbiddenWords;
    private CommentRepository commentRepository;
    private CommentConverter commentConverter;

    public CommentServiceImpl(CommentRepository commentRepository, CommentConverter commentConverter) {
        this.commentRepository = commentRepository;
        this.commentConverter = commentConverter;
    }

    @Override
    @Transactional
    public boolean addComment(CommentDTO commentDTO) {
        if (!validComment(commentDTO.getContent())) {
            return false;
        }
        Comment comment = commentConverter.toComment(commentDTO);
        commentRepository.add(comment);
        return true;
    }

    private boolean validComment(String content) {
        String[] forbiddenArray = forbiddenWords.split(",");
        for (String word : forbiddenArray) {
            if (content.contains(word)) {
                return false;
            }
        }
        return true;
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId);
        comment.setAuthor(null);
        comment.setArticle(null);
        comment.setHeadComment(null);
        commentRepository.remove(comment);
    }
}
