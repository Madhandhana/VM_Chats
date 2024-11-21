package com.example.vmchats;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
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
import com.example.vmchats.utils.ImageUtil;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.IOException;
import java.io.InputStream;

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

        imagePicLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getData() != null) {
                            selectedImageUri = data.getData();
                            Bitmap bitmap = null;
                            try {
                                InputStream inputStream = getContext().getContentResolver().openInputStream(selectedImageUri);
                                if (inputStream != null) {
                                    bitmap = BitmapFactory.decodeStream(inputStream);
                                    inputStream.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (bitmap != null) {
                                // Store and update the profile picture
                                String base64Image = ImageUtil.bitmapToBase64(bitmap);
                                currentUserModel.setProfilePictureBase64(base64Image);
                                ImageUtil.storeImageInFirestore(FirebaseUtil.currentUserId(), bitmap);
                                AndroidUtil.setProfilePic(getContext(), selectedImageUri, profilePic);
                            }
                        }
                    }
                });


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

    void updateBtnClick() {
        String newUsername = userNameInput.getText().toString();
        if (newUsername.isEmpty() || newUsername.length() < 3) {
            userNameInput.setError("Username is required");
            return;
        }
        currentUserModel.setUsername(newUsername);

        // Ensure profilePictureBase64 is retained
        if (currentUserModel.getProfilePictureBase64() == null || currentUserModel.getProfilePictureBase64().isEmpty()) {
            currentUserModel.setProfilePictureBase64(""); // Set a default value if needed
        }

        setInProgress(true);
        updateToFirestore();
    }
    void updateToFirestore() {
        FirebaseUtil.currentUserDetails().set(currentUserModel).addOnCompleteListener(task -> {
            setInProgress(false);
            if (task.isSuccessful()) {
                AndroidUtil.showToast(getContext(), "Profile Updated");
            } else {
                AndroidUtil.showToast(getContext(), "Failed to update profile");
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
            if (currentUserModel.getProfilePictureBase64() != null) {
                Bitmap bitmap = ImageUtil.base64ToBitmap(currentUserModel.getProfilePictureBase64());
                AndroidUtil.setProfilePic(getContext(), bitmap, profilePic);
            }
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