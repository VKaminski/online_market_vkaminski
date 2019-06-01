package com.gmail.kaminski.viktar.onlinemarket.service.model.util;

import java.util.Date;

public class ArticlesRequestDTO {
    private String title;
    private Date dateStart;
    private Date dateStop;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStop(Date dateStop) {
        this.dateStop = dateStop;
    }

    public Date getDateStop() {
        return dateStop;
    }
}
