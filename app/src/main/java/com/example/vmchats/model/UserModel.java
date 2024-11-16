package com.example.vmchats.model;


import com.google.firebase.Timestamp;

public class UserModel {
    private String username;
    private String phone;
    private Timestamp createdTimestamp;

    public UserModel() {
    }
    public UserModel(String phone, String username, Timestamp createdTimestamp) {
        this.username = username;
        this.phone = phone;

        this.createdTimestamp = createdTimestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }
}
