package com.example.vmchats;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vmchats.utils.AndroidUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class login_otp extends AppCompatActivity {

    String phoneNumber;
    String verificationcode;
    PhoneAuthProvider.ForceResendingToken resendingToken;
    Long timeoutSeconds = 30L;
    EditText otpinput;
    Button nxtBtn;
    ProgressBar progressBar;
    TextView resendOtpTextView;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp);

        otpinput = findViewById(R.id.otp);
        nxtBtn = findViewById(R.id.otp_next);
        progressBar = findViewById(R.id.progress);
        resendOtpTextView = findViewById(R.id.resend_otp);
        phoneNumber = getIntent().getStringExtra("phone");
        sendOtp(phoneNumber, false);

        nxtBtn.setOnClickListener(v->{
            String enteredOtp = otpinput.getText().toString();
           PhoneAuthCredential credential=  PhoneAuthProvider.getCredential(verificationcode, enteredOtp);
            signin(credential);

        });
        resendOtpTextView.setOnClickListener(v->{
            sendOtp(phoneNumber, true);
            startResendTimer();
        });

    }

    void sendOtp(String phoneNumber , boolean isResend) {

        startResendTimer();
        setInProgress(true);
        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(timeoutSeconds, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signin(phoneAuthCredential);
                        setInProgress(false);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        AndroidUtil.showToast(getApplicationContext(),e.toString());
                        setInProgress(false);
                    }
                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        verificationcode = s;
                        resendingToken = forceResendingToken;
                        AndroidUtil.showToast(getApplicationContext(),"Otp sent successfully");
                        setInProgress(false);

                    }
                });

        if(isResend){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        }else{
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }


    }
    void setInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            nxtBtn.setVisibility(View.GONE);
        }
        else{
            progressBar.setVisibility(View.GONE);
            nxtBtn.setVisibility(View.VISIBLE);
        }
    }

    void signin(PhoneAuthCredential phoneAuthCredential){
        setInProgress(true);
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                setInProgress(false);
                if(task.isSuccessful()){
                    Intent intent = new Intent(login_otp.this, Username.class);
                    intent.putExtra("phone", phoneNumber);
                    startActivity(intent);
                }else{
                    AndroidUtil.showToast(getApplicationContext(),"Login Failed");
                }
            }
        });
    }

    void startResendTimer(){
        resendOtpTextView.setEnabled(false);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeoutSeconds--;
                resendOtpTextView.setText("Resend OTP in " + timeoutSeconds + " seconds");
                if (timeoutSeconds <= 0) {
                    timeoutSeconds = 30L;
                    timer.cancel();
                    runOnUiThread(() -> {
                        resendOtpTextView.setEnabled(true);

                    });
                }
            }
        },0,1000);
    }

}