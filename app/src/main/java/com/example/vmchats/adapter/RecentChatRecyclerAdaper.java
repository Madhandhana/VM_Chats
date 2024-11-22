package com.example.vmchats.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vmchats.ChatActivity;
import com.example.vmchats.R;
import com.example.vmchats.model.ChatroomModel;
import com.example.vmchats.model.UserModel;
import com.example.vmchats.utils.AndroidUtil;
import com.example.vmchats.utils.FirebaseUtil;
import com.example.vmchats.utils.ImageUtil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class RecentChatRecyclerAdaper extends FirestoreRecyclerAdapter<ChatroomModel,RecentChatRecyclerAdaper.ChatroomModelViewHolder> {

    Context context;
    public RecentChatRecyclerAdaper(@NonNull FirestoreRecyclerOptions<ChatroomModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatroomModelViewHolder holder, int position, @NonNull ChatroomModel model) {
        FirebaseUtil.getOtherUserFromChatRoom(model.getUserIds())
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        boolean lastmessageSentByMe = model.getLastMessageSenderId() != null && model.getLastMessageSenderId().equals(FirebaseUtil.currentUserId());
                        UserModel otherUser = task.getResult().toObject(UserModel.class);
                        ImageUtil.retrieveImageFromFirestore(otherUser.getUserId(), holder.profilePic, context);

                        if (otherUser != null) {
                        holder.usernameText.setText(otherUser.getUsername());

                        }
                        if(lastmessageSentByMe) {
                            holder.lastMessageText.setText("You: " + model.getLastMessage());
                        } else {
                            holder.lastMessageText.setText(model.getLastMessage());
                        }

                        holder.lastMessageTime.setText(FirebaseUtil.timestampToSting(model.getLastMessageTimestamp()));

                        holder.itemView.setOnClickListener(v -> {
                            Intent intent = new Intent(context, ChatActivity.class);
                            AndroidUtil.passUserModelAsIntent(intent,otherUser);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        });
                    }
                });

    }

    @NonNull
    @Override
    public ChatroomModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_chat_recycler_row,parent,false);
        return new ChatroomModelViewHolder(view);

    }

    class ChatroomModelViewHolder extends RecyclerView.ViewHolder {

        TextView usernameText;
        TextView lastMessageText;

        TextView lastMessageTime;
        ImageView profilePic;

        public ChatroomModelViewHolder(@NonNull View itemView) {
            super(itemView);

            usernameText = itemView.findViewById(R.id.user_name_text);
            lastMessageText = itemView.findViewById(R.id.last_message_text);
            lastMessageTime = itemView.findViewById(R.id.last_message_time_text);
            profilePic = itemView.findViewById(R.id.profile_pic_image_view);


        }

    }
}
