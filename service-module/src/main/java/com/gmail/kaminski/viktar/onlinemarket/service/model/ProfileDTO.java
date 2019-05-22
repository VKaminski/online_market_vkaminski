package com.gmail.kaminski.viktar.onlinemarket.service.model;

public class ProfileDTO {
    private UserDTO user;
    private String address;
    private String phone;

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }
}
