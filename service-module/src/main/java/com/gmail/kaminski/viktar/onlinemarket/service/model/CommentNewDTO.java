package com.gmail.kaminski.viktar.onlinemarket.service.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommentNewDTO {
    private Long articleId;
    private Long authorId;
    @NotNull
    @Size(min = 1, max = 200)
    private String content;
    private Long headCommentId;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getHeadCommentId() {
        return headCommentId;
    }

    public void setHeadCommentId(Long headCommentId) {
        this.headCommentId = headCommentId;
    }
}
