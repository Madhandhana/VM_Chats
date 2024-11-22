package com.example.vmchats;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

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

    private Switch biometricEnableSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        executor = ContextCompat.getMainExecutor(this);



        biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

            }


            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                proceedToMainActivity();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                retryAuthentication();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Authenticate to use VM Chats")
                .setSubtitle("Authenticate using your biometric credential")
                .setDeviceCredentialAllowed(true)
                .build();

        biometricPrompt.authenticate(promptInfo);


    Button unlockButton = findViewById(R.id.unlock_button);
    unlockButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            biometricPrompt.authenticate(promptInfo);
    }
    });

}
    private void retryAuthentication() {
        biometricPrompt.authenticate(promptInfo);
    }

    private void proceedToMainActivity() {
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
        finish();
    }

    }
