package com.gmail.kaminski.viktar.onlinemarket.service.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class ArticleNewDTO {
    @NotNull
    @Size(min = 1, max = 50)
    private String title;
    @NotNull
    @Size(min = 1, max = 1000)
    private String content;
    private Long userId;
    private Date date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
