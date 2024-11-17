package com.example.vmchats;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vmchats.model.UserModel;
import com.example.vmchats.utils.AndroidUtil;
import com.example.vmchats.utils.FirebaseUtil;
import com.github.dhaval2404.imagepicker.ImagePicker;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class ProfileFragment extends Fragment {

    ImageView profilePic;

    EditText userNameInput;
    EditText phoneInput;
    Button updateProfileButton;
    ProgressBar progressBar;
    TextView logoutButton;
    UserModel currentUserModel;
    ActivityResultLauncher<Intent> imagePicLauncher;
    Uri selectedImageUri;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePicLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data != null && data.getData()!=null){
                            selectedImageUri = data.getData();
                            AndroidUtil.setProfilePic(getContext(),selectedImageUri,profilePic);
                    }
                }
    });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profilePic = view.findViewById(R.id.profile_image_view);
        phoneInput = view.findViewById(R.id.profile_phone);
        updateProfileButton = view.findViewById(R.id.profile_update_btn);
        userNameInput = view.findViewById(R.id.profile_username);
        progressBar = view.findViewById(R.id.profile_progress_bar);
        logoutButton = view.findViewById(R.id.logout_btn);

        getUserData();
        updateProfileButton.setOnClickListener(v -> {
            updateBtnClick();

        });
        logoutButton.setOnClickListener(v->{
            FirebaseUtil.logout();
            Intent intent = new Intent(getContext(),SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
        profilePic.setOnClickListener(v->{
            ImagePicker.with(this)
                    .cropSquare()
                    .compress(512)
                    .maxResultSize(512,512)
                    .createIntent(new Function1<Intent, Unit>() {
                        @Override
                        public Unit invoke(Intent intent) {
                            imagePicLauncher.launch(intent);
                            return null;
                        }


                    });
        });
        return view;
    }

    void updateBtnClick(){
        String newusername = userNameInput.getText().toString();
        if(newusername.isEmpty() || newusername.length()<3){
            userNameInput.setError("Username is required");
            return;
        }
        currentUserModel.setUsername(newusername);
        setInProgress(true);
        updateToFirestore();
    }

    void updateToFirestore(){

        FirebaseUtil.currentUserDetails().set(currentUserModel).addOnCompleteListener(task -> {
            setInProgress(false);
            if(task.isSuccessful()){
                AndroidUtil.showToast(getContext(),"Profile Updated");
            }
            else{
                AndroidUtil.showToast(getContext(),"Failed to update profile");
            }
        });
    }


    void getUserData(){
        setInProgress(true);
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(task -> {
            setInProgress(false);
                currentUserModel = task.getResult().toObject(UserModel.class);
                userNameInput.setText(currentUserModel.getUsername());
                phoneInput.setText(currentUserModel.getPhone());

        });
    }

    void setInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            updateProfileButton.setVisibility(View.GONE);
        }
        else{
            progressBar.setVisibility(View.GONE);
            updateProfileButton.setVisibility(View.VISIBLE);
        }
    }


}