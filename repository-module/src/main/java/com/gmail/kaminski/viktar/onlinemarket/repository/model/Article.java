package com.gmail.kaminski.viktar.onlinemarket.repository.model;

import java.util.Date;
import java.util.List;

public class Article {
    private Long id;
    private User author;
    private String content;
    private Date date;
    private Integer amountComments;
    private String title;
    private List<Comment> comments;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getAuthor() {
        return author;
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

    public void setAmountComments(Integer amountComments) {
        this.amountComments = amountComments;
    }

    public Integer getAmountComments() {
        return amountComments;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
