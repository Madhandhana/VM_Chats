package com.example.vmchats; 
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
        setContentView(R.layout.activity_splash);


        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            if(FirebaseUtil.isLoggedIn()){
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(SplashActivity.this, LoginPhoneNumberActivity.class);
                startActivity(intent);

            }
            finish();
        }, 1000);

    }
}
