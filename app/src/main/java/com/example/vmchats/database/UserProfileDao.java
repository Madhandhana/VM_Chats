package com.example.vmchats.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Delete;

@Dao
public interface UserProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserProfile(UserProfile userProfile);

    @Query("SELECT * FROM user_profile WHERE userId = :userId")
    UserProfile getUserProfile(String userId);

    @Query("DELETE FROM user_profile WHERE userId = :userId")
    void deleteUserProfile(String userId);
}