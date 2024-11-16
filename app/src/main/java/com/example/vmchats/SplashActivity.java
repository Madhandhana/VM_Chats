package com.example.vmchats; // Make sure this matches the package in your manifest.

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // Ensure this XML layout exists.

        // Add logic to move to the next activity after some delay or initialization
        new Thread(() -> {
            try {
                // Simulate some work, like loading resources, fetching data, etc.
                Thread.sleep(2000); // 2-second delay for splash screen
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // After the splash screen, launch the main activity
            Intent intent = new Intent(SplashActivity.this, loginphonenumber.class);
            startActivity(intent);
            finish(); // Close the splash activity so the user can't navigate back to it
        }).start();

    }
}
