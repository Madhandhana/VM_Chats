package com.example.vmchats.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.example.vmchats.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;

public class ImageUtil {

    private static final String TAG = "ImageUtil";

    // Convert Bitmap to Base64 string
    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    // Store Base64 string in Firestore
    public static void storeImageInFirestore(String userId, Bitmap bitmap) {
        String base64Image = bitmapToBase64(bitmap);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId)
                .update("profilePictureBase64", base64Image)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Image stored successfully "+base64Image.length());
                    } else {
                        Log.e(TAG, "Failed to store image", task.getException());
                    }
                });
    }

    // Convert Base64 string to Bitmap
    public static Bitmap base64ToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    // Retrieve Base64 string from Firestore and convert to Bitmap
    public static void retrieveImageFromFirestore(String userId, ImageView imageView, Context context) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        String base64Image = task.getResult().getString("profilePictureBase64");
                        if (base64Image != null) {
                            Bitmap bitmap = base64ToBitmap(base64Image);
                            Log.d(TAG, "Image retreived successfully "+base64Image.length());
                            if (bitmap != null) {
                                AndroidUtil.setProfilePic(context, bitmap, imageView);
                            } else {
                                imageView.setImageResource(R.drawable.user); // Use a default drawable
                            }

                        }
                    } else {
                        Log.e(TAG, "Failed to retrieve image", task.getException());
                    }
                });
    }
}