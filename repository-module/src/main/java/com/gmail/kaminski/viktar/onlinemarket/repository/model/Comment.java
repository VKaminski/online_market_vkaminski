package com.gmail.kaminski.viktar.onlinemarket.repository.model;

<<<<<<< HEAD
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Entity
@Table
@SQLDelete(sql =
        "UPDATE Comment " +
                "SET deleted = true " +
                "WHERE id = ?")
@Where(clause = "deleted = false")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "article_id")
    private Article article;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
    @Column
    private String content;
    @Column
    private Date date;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "head_comment_id")
    private Comment headComment;
    @Column
    private boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
=======
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
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f
    }

    public User getAuthor() {
        return author;
    }

<<<<<<< HEAD
    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
=======
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f
    }

    public void setContent(String content) {
        this.content = content;
    }

<<<<<<< HEAD
    public Date getDate() {
        return date;
=======
    public String getContent() {
        return content;
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f
    }

    public void setDate(Date date) {
        this.date = date;
    }

<<<<<<< HEAD
    public Comment getHeadComment() {
        return headComment;
    }

    public void setHeadComment(Comment headComment) {
        this.headComment = headComment;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, article, author, content, date, headComment);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj != null || getClass() != obj.getClass()){
            return false;
        }
        Comment comment = (Comment) obj;
        return Objects.equals(id, comment.id) &&
                Objects.equals(author.getId(), comment.author.getId()) &&
                Objects.equals(date, comment.date) &&
                Objects.equals(headComment.id, comment.headComment.id) &&
                Objects.equals(content, comment.content);
=======
    public Date getDate() {
        return date;
    }

    public void setHead(Long head) {
        this.head = head;
    }

    public Long getHead() {
        return head;
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f
    }
}
