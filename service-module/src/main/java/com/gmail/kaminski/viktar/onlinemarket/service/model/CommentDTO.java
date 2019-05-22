package com.gmail.kaminski.viktar.onlinemarket.service.model;

import java.util.Date;

public class CommentDTO {
    private Long articleId;
    private Long id;
    private UserDTO author;
    private String content;
    private Date date;
    private Long head;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    public UserDTO getAuthor() {
        return author;
    }

    public void setHead(Long head) {
        this.head = head;
    }

    public Long getHead() {
        return head;
    }
}
