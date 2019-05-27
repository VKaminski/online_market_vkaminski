package com.gmail.kaminski.viktar.onlinemarket.repository.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
        "UPDATE User " +
                "SET deleted = true " +
                "WHERE id = ?")
@Where(clause = "deleted = false")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column
    private String content;
    @Column
    private Date date;
    @Column
    private Boolean visible;
    @Column
    private Boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, content, date);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null || getClass() != obj.getClass()) {
            return false;
        }
        Review review = (Review) obj;
        return Objects.equals(id, review.id) &&
                Objects.equals(user.getId(), review.user.getId()) &&
                Objects.equals(content, review.content);
    }
}
