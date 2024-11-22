package com.example.vmchats.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {UserProfile.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserProfileDao userProfileDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "user_profile_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}