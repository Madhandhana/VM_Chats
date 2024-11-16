package com.example.vmchats;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.hbb20.CountryCodePicker;
public class LoginPhoneNumberActivity extends AppCompatActivity {
    CountryCodePicker countrycodepicker;
    EditText phoneinput;
    Button sendOtpBtn;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginphonenumber);
        countrycodepicker = findViewById(R.id.login_countrycode);
        phoneinput = findViewById(R.id.login_mobile_number);
        sendOtpBtn = findViewById(R.id.login_otp);
        progressBar = findViewById(R.id.progress);

        progressBar.setVisibility(View.GONE);

        countrycodepicker.registerCarrierNumberEditText(phoneinput);
        sendOtpBtn.setOnClickListener((v)-> {
            if (!countrycodepicker.isValidFullNumber()) {
                phoneinput.setError("Invalid Phone Number");
                return;
            }
            Intent intent = new Intent(LoginPhoneNumberActivity.this, login_otp.class);
            intent.putExtra("phone", countrycodepicker.getFullNumberWithPlus());
            startActivity(intent);
        });
    }
}