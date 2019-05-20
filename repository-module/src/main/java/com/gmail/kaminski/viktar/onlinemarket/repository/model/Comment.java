package com.gmail.kaminski.viktar.onlinemarket.repository.model;

import java.util.Date;

public class Comment {
    private Long articleId;
    private User author;
    private Long id;
    private String content;
    private Date date;
    private Long head;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getAuthor() {
        return author;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public void setHead(Long head) {
        this.head = head;
    }

    public Long getHead() {
        return head;
    }
}
