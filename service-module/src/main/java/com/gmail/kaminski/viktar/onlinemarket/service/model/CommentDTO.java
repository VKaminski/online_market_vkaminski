package com.gmail.kaminski.viktar.onlinemarket.service.model;

import java.util.Date;

public class CommentDTO {
    private Long id;
    private Long articleId;
    private UserDTO author;
    private String content;
    private Date date;
    private Long headCommentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getHeadCommentId() {
        return headCommentId;
    }

    public void setHeadCommentId(Long headCommentId) {
        this.headCommentId = headCommentId;
    }
}
