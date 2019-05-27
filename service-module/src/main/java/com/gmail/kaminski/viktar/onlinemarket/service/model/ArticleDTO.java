package com.gmail.kaminski.viktar.onlinemarket.service.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleDTO {
    private Long id;
    private UserDTO author;
    private String title;
    private String content;
    private Date date;
    private List<CommentDTO> comments = new ArrayList<>();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    public UserDTO getAuthor() {
        return author;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }
}
