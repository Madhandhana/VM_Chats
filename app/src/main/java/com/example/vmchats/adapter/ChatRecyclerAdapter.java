package com.example.vmchats.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vmchats.ChatActivity;
import com.example.vmchats.R;
import com.example.vmchats.model.ChatMessageModel;
import com.example.vmchats.utils.AndroidUtil;
import com.example.vmchats.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ChatRecyclerAdapter extends FirestoreRecyclerAdapter<ChatMessageModel,ChatRecyclerAdapter.ChatModelViewHolder> {

    Context context;
    public ChatRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ChatMessageModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatModelViewHolder holder, int position, @NonNull ChatMessageModel model) {
        if(model.getSenderId().equals(FirebaseUtil.currentUserId())) {
            holder.rightchatlayout.setVisibility(View.VISIBLE);
            holder.leftchatlayout.setVisibility(View.GONE);
            holder.rightchatTextView.setText(model.getMessage());
        }else {
            holder.rightchatlayout.setVisibility(View.GONE);
            holder.leftchatlayout.setVisibility(View.VISIBLE);
            holder.leftchatTextView.setText(model.getMessage());
        }
    }

    @NonNull
    @Override
    public ChatModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_messsage_recycler_row,parent,false);
        return new ChatModelViewHolder(view);

    }

    class ChatModelViewHolder extends RecyclerView.ViewHolder {

       
        LinearLayout leftchatlayout , rightchatlayout;
        TextView leftchatTextView , rightchatTextView;
        public ChatModelViewHolder(@NonNull View itemView) {
            super(itemView);

            leftchatlayout = itemView.findViewById(R.id.left_chat_layout);
            rightchatlayout = itemView.findViewById(R.id.right_chat_layout);
            leftchatTextView = itemView.findViewById(R.id.left_chat_textview);
            rightchatTextView = itemView.findViewById(R.id.right_chat_textview);


        }

    }
}
