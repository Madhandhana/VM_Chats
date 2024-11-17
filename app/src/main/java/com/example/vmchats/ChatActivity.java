package com.example.vmchats;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vmchats.model.UserModel;
import com.example.vmchats.utils.AndroidUtil;

import org.w3c.dom.Text;

public class ChatActivity extends AppCompatActivity {

    UserModel otherUser;

    EditText messageInput;
    ImageButton sendMessageBtn;
    ImageButton backBtn;
    TextView otherUsername;

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        otherUser = AndroidUtil.getUserModelFromIntent(getIntent());
        messageInput = findViewById(R.id.chat_message_input);
        sendMessageBtn = findViewById(R.id.message_send_btn);
        backBtn = findViewById(R.id.back_button); // This is now a TextView
        otherUsername = findViewById(R.id.other_username);
        recyclerView = findViewById(R.id.chat_recycler_view);

        // Set the back button functionality
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ChatActivity.this, SearchUserActivity.class); // Replace with the target activity
            startActivity(intent);
            finish();
        });

        // Set the other username
        otherUsername.setText(otherUser.getUsername());

        getOrCreateChatroomModel();
    }

    void getOrCreateChatroomModel(){

    }

}