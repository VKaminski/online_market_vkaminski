package com.gmail.kaminski.viktar.onlinemarket.service.model;

<<<<<<< HEAD
import java.util.ArrayList;
=======
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f
import java.util.Date;
import java.util.List;

public class ArticleDTO {
    private Long id;
    private UserDTO author;
    private String title;
    private String content;
    private Date date;
<<<<<<< HEAD
    private List<CommentDTO> comments = new ArrayList<>();
=======
    private Integer amountComments;
    private String preview;
    private List<CommentDTO> comments;
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f

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

<<<<<<< HEAD
=======
    public void setAmountComments(Integer amountComments) {
        this.amountComments = amountComments;
    }

    public Integer getAmountComments() {
        return amountComments;
    }

>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f
    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    public UserDTO getAuthor() {
        return author;
    }

<<<<<<< HEAD
=======
    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getPreview() {
        return preview;
    }

>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f
    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }
}
