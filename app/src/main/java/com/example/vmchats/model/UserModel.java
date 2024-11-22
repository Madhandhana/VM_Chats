package com.example.vmchats.model;


import com.google.firebase.Timestamp;

public class UserModel {
    private String username;
    private String phone;
    private Timestamp createdTimestamp;

    private String userId;
    private String profilePictureBase64="";

    public UserModel() {
    }
    public UserModel(String phone, String username, Timestamp createdTimestamp, String userId) {
        this.phone = phone;
        this.username = username;
        this.createdTimestamp = createdTimestamp;
        this.userId = userId;
        this.profilePictureBase64 = "";
    }

    public UserModel(String phone, String username, Timestamp createdTimestamp, String userId, String profilePictureBase64) {
        this.phone = phone;
        this.username = username;
        this.createdTimestamp = createdTimestamp;
        this.userId = userId;
        this.profilePictureBase64 = profilePictureBase64;
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


    public String getProfilePictureBase64() {
        return profilePictureBase64;
    }

    public void setProfilePictureBase64(String profilePictureBase64) {
        this.profilePictureBase64 = profilePictureBase64;
    }
}
