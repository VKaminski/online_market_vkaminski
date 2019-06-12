package com.gmail.kaminski.viktar.onlinemarket.service.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ProfileEditDTO {
    private Long id;
    @NotNull
    @Size(min = 1, max = 20)
    @Pattern(regexp = "[a-zA-Z]*")
    private String name;
    @NotNull
    @Size(min = 1, max = 40)
    @Pattern(regexp = "[a-zA-Z]*")
    private String surname;
    @Size(max = 40)
    private String address;
    @Size(min = 12, max = 12)
    @Pattern(regexp = "[0-9]*")
    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
