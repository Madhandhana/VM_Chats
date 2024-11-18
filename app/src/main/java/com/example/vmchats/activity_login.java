package com.example.vmchats;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.Executor;

public class activity_login extends AppCompatActivity {

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        executor = ContextCompat.getMainExecutor(this);

        // Initialize the biometric prompt
        biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                proceedToMainActivity(); // Proceed to main activity on success
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                retryAuthentication(); // Retry on authentication failure
            }
        });

        // Set up the prompt info
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Authenticate to use VM Chats")
                .setSubtitle("Authenticate using your biometric credential")
                .setDeviceCredentialAllowed(true)
                .build();

        // Trigger the biometric prompt immediately when the app opens
        biometricPrompt.authenticate(promptInfo);
    }

    private void retryAuthentication() {
        biometricPrompt.authenticate(promptInfo);
    }

    private void proceedToMainActivity() {
        // Navigate to the MainActivity
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
        finish(); // Close the current activity
    }

    }
