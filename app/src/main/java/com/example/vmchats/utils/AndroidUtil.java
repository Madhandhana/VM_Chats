package com.example.vmchats.utils;

import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.vmchats.model.UserModel;

public class AndroidUtil
{

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void passUserModelAsIntent(Intent intent , UserModel userModel){

        intent.putExtra("username",userModel.getUsername());
        intent.putExtra("phone",userModel.getPhone());
        intent.putExtra("userId",userModel.getUserId());
    }
    public static UserModel getUserModelFromIntent(Intent intent){
        UserModel userModel= new UserModel();
        userModel.setUsername(intent.getStringExtra("username"));
        userModel.setPhone(intent.getStringExtra("phone"));
        userModel.setUserId(intent.getStringExtra("userId"));
        return userModel;
    }

    public static void setProfilePic(Context context, Uri imageuri, ImageView imageView){
        Glide.with(context).load(imageuri).apply(new RequestOptions().circleCropTransform()).into(imageView);
    }
}
