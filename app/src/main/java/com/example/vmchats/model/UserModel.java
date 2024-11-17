package com.example.vmchats.model;


import com.google.firebase.Timestamp;

public class UserModel {
    private String username;
    private String phone;
    private Timestamp createdTimestamp;

    private String userId;

    public UserModel() {
    }
    public UserModel(String phone, String username, Timestamp createdTimestamp, String userId) {
        this.username = username;
        this.phone = phone;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
