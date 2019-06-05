package com.gmail.kaminski.viktar.onlinemarket.service.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.ArticleRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.CommentRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.UserRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Article;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Comment;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.User;
import com.gmail.kaminski.viktar.onlinemarket.service.CommentService;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.CommentConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.CommentNewDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {
    @Value("${custom.comment.forbidden.word}")
    private String forbiddenWords;
    private CommentRepository commentRepository;
    private CommentConverter commentConverter;
    private UserRepository userRepository;
    private ArticleRepository articleRepository;

    public CommentServiceImpl(CommentRepository commentRepository,
                              CommentConverter commentConverter,
                              UserRepository userRepository,
                              ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.commentConverter = commentConverter;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    @Transactional
    public boolean addComment(CommentNewDTO commentDTO) {
        if (!validComment(commentDTO.getContent())) {
            return false;
        }
        Comment comment = new Comment();
        User author = userRepository.findById(commentDTO.getAuthorId());
        comment.setAuthor(author);
        if (commentDTO.getHeadCommentId() != null) {
            Comment headComment = commentRepository.findById(commentDTO.getHeadCommentId());
            comment.setHeadComment(headComment);
        }
        Article article = articleRepository.findById(commentDTO.getArticleId());
        comment.setDate(new Date(System.currentTimeMillis()));
        comment.setArticle(article);
        comment.setContent(commentDTO.getContent());
        commentRepository.add(comment);
        return true;
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId);
        commentRepository.remove(comment);
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
}
