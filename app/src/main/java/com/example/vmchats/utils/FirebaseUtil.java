package com.example.vmchats.utils;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.List;

public class FirebaseUtil {

    public static  String currentUserId(){
        return FirebaseAuth.getInstance().getUid();
    }

    public static boolean isLoggedIn(){
        if(currentUserId()!=null){
            return true;
        }
        return false;
    }
    public static DocumentReference currentUserDetails(){
     return FirebaseFirestore.getInstance().collection("users").document(currentUserId());
    }

    public static CollectionReference allUserCollectionReferece(){
        return FirebaseFirestore.getInstance().collection("users");
    }

    public static DocumentReference getChatroomReference(String chatroomId){
        return FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId);
    }

    public static CollectionReference getChatroomMessageReference(String chatroomId){
        return getChatroomReference(chatroomId).collection("chats");

    }

    public static  String getChatroomid(String userId1 , String userId2){
        if(userId1.hashCode()<userId2.hashCode()){
            return userId1+"_"+userId2;
        }
        else{
            return userId2+"_"+userId1;
        }
    }

    public static CollectionReference allChatroomCollectionReference(){
        return FirebaseFirestore.getInstance().collection("chatrooms");
    }

    public static DocumentReference getOtherUserFromChatRoom(List<String> userIds){
        if(userIds.get(0).equals(currentUserId())){
            return allUserCollectionReferece().document(userIds.get(1));
        }
        else{
            return allUserCollectionReferece().document(userIds.get(0));
        }



    }

    public static String timestampToSting(Timestamp timestamp){
        return new SimpleDateFormat("HH:MM").format(timestamp.toDate());
    }

  public static void logout(){
        FirebaseAuth.getInstance().signOut();
  }
}
