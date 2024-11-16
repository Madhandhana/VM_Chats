package com.example.vmchats;

import android.os.Bundle;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class login_otp extends AppCompatActivity {

    String phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp);
        phoneNumber = getIntent().getStringExtra("phone");
        Toast.makeText(getApplicationContext(),phoneNumber,Toast.LENGTH_LONG).show();

        Map<String, String> data = new HashMap<>();
        FirebaseFirestore.getInstance().collection("test").add(data);
    }
}