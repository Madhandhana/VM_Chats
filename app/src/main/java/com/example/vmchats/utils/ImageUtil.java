package com.example.vmchats.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.example.vmchats.R;
import com.example.vmchats.database.AppDatabase;
import com.example.vmchats.database.UserProfile;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;

public class ImageUtil {

    private static final String TAG = "ImageUtil";


    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }


    public static void storeImageInFirestore(String userId, Bitmap bitmap, Context context) {
        String base64Image = bitmapToBase64(bitmap);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId)
                .update("profilePictureBase64", base64Image)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Image stored successfully " + base64Image.length());

                        AsyncTask.execute(() -> {
                            AppDatabase.getDatabase(context).userProfileDao().deleteUserProfile(userId);
                            UserProfile userProfile = new UserProfile();
                            userProfile.userId = userId;
                            userProfile.profilePictureBase64 = base64Image;
                            AppDatabase.getDatabase(context).userProfileDao().insertUserProfile(userProfile);
                        });
                    } else {
                        Log.e(TAG, "Failed to store image", task.getException());
                    }
                });
    }


    public static Bitmap base64ToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public static void retrieveImageFromFirestore(String userId, ImageView imageView, Context context) {
        AsyncTask.execute(() -> {
            UserProfile userProfile = AppDatabase.getDatabase(context).userProfileDao().getUserProfile(userId);
            if (userProfile != null && userProfile.profilePictureBase64 != null) {
                Bitmap bitmap = base64ToBitmap(userProfile.profilePictureBase64);
                if (bitmap != null) {
                    new Handler(Looper.getMainLooper()).post(() -> {
                        AndroidUtil.setProfilePic(context, bitmap, imageView);
                    });
                } else {
                    new Handler(Looper.getMainLooper()).post(() -> {
                        imageView.setImageResource(R.drawable.user);
                    });
                }
            } else {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users").document(userId)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful() && task.getResult() != null) {
                                String base64Image = task.getResult().getString("profilePictureBase64");
                                if (base64Image != null) {
                                    Bitmap bitmap = base64ToBitmap(base64Image);
                                    if (bitmap != null) {
                                        new Handler(Looper.getMainLooper()).post(() -> {
                                            AndroidUtil.setProfilePic(context, bitmap, imageView);
                                        });

                                        AsyncTask.execute(() -> {
                                            AppDatabase.getDatabase(context).userProfileDao().deleteUserProfile(userId);
                                            UserProfile newUserProfile = new UserProfile();
                                            newUserProfile.userId = userId;
                                            newUserProfile.profilePictureBase64 = base64Image;
                                            AppDatabase.getDatabase(context).userProfileDao().insertUserProfile(newUserProfile);
                                        });
                                    } else {
                                        new Handler(Looper.getMainLooper()).post(() -> {
                                            imageView.setImageResource(R.drawable.user);
                                        });
                                    }
                                }
                            } else {
                                Log.e(TAG, "Failed to retrieve image", task.getException());
                            }
                        });
            }
        });
    }
}