package com.gmail.kaminski.viktar.onlinemarket.service.model;

import java.util.Date;

public class CommentDTO {
    private Long id;
    private ArticleDTO article;
    private UserDTO author;
    private String content;
    private Date date;
    private CommentDTO headComment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArticleDTO getArticle() {
        return article;
    }

    public void setArticle(ArticleDTO article) {
        this.article = article;
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

    public CommentDTO getHeadComment() {
        return headComment;
    }

    public void setHeadComment(CommentDTO headComment) {
        this.headComment = headComment;
    }
}
