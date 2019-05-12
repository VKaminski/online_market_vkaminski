package com.gmail.kaminski.viktar.onlinemarket.controller.model;

import java.util.List;

public class DeletedList {
    private List<Long> checkedUsersId;

    public List<Long> getCheckedUsersId() {
        return checkedUsersId;
    }

    public void setCheckedUsersId(List<Long> checkedUsersId) {
        this.checkedUsersId = checkedUsersId;
    }
}
