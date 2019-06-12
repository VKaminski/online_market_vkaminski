package com.gmail.kaminski.viktar.onlinemarket.service.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserNewDTO {
    @NotNull
    @Size(min = 1, max = 20)
    @Pattern(regexp = "[a-zA-Z]*")
    private String name;
    @NotNull
    @Size(min = 1, max = 40)
    @Pattern(regexp = "[a-zA-Z]*")
    private String surname;
    @Size(min = 1, max = 40)
    @Pattern(regexp = "[a-zA-Z]*")
    private String patronymic;
    @NotNull
    @Email
    @Size(max = 50)
    private String email;
    @NotNull
    private RoleDTO role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }
}
