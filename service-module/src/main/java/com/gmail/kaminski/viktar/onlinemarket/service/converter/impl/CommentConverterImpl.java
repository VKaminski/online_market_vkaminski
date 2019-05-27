package com.gmail.kaminski.viktar.onlinemarket.service.converter.impl;

<<<<<<< HEAD
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Article;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Comment;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.ArticleConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.CommentConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.UserConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
=======
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Comment;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.CommentConverter;
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f
import com.gmail.kaminski.viktar.onlinemarket.service.model.CommentDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentConverterImpl implements CommentConverter {
<<<<<<< HEAD
    private UserConverter userConverter;

    public CommentConverterImpl(UserConverter userConverter) {
=======
    private UserConverterImpl userConverter;

    public CommentConverterImpl(UserConverterImpl userConverter) {
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f
        this.userConverter = userConverter;
    }

    @Override
    public CommentDTO toCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
<<<<<<< HEAD
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
=======
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
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f
        }
        return commentDTO;
    }

    @Override
    public Comment toComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
<<<<<<< HEAD
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
=======
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
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f
        }
        return comment;
    }
}
