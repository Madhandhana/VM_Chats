package com.example.vmchats.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_profile")
public class UserProfile {
    @PrimaryKey
    @NonNull
    public String userId;
    public String profilePictureBase64;
}