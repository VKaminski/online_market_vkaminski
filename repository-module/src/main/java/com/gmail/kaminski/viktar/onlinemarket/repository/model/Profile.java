package com.gmail.kaminski.viktar.onlinemarket.repository.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table
public class Profile {
    @Id
    @Column(name = "id")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId
    private User user;
    @Column
    private String address;
    @Column
    private String phone;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
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

    @Override
    public int hashCode() {
        return Objects.hash(id, user, address, phone);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null || getClass() != obj.getClass()) {
            return false;
        }
        Profile profile = (Profile) obj;
        return Objects.equals(id, profile.id) &&
                Objects.equals(user.getId(), profile.user.getId()) &&
                Objects.equals(address, profile.address) &&
                Objects.equals(phone, profile.phone);
    }
}
