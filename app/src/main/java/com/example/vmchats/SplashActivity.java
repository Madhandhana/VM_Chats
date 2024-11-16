package com.example.vmchats; // Make sure this matches the package in your manifest.

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vmchats.utils.FirebaseUtil;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // Ensure this XML layout exists.

        // Add logic to move to the next activity after some delay or initialization
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // After the splash screen, launch the main activity
            if(FirebaseUtil.isLoggedIn()){
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(SplashActivity.this, LoginPhoneNumberActivity.class);
                startActivity(intent);

            }
            finish();// Close the splash activity so the user can't navigate back to it
        }, 1000);

    }
}
