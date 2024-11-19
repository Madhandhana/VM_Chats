package com.example.vmchats;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;
import com.example.vmchats.adapter.ViewPagerAdapter;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    BottomNavigationView bottomNavigationView;
    ImageButton searchButton;
    ChatFragment chatFragment;
    ProfileFragment profileFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chatFragment = new ChatFragment();
        profileFragment = new ProfileFragment();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        searchButton = findViewById(R.id.main_search_btn);

        viewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
        searchButton.setOnClickListener((v)->{
    startActivity(new Intent(MainActivity.this, SearchUserActivity.class));
        });



        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.menu_chat){
                    viewPager.setCurrentItem(0);
                } else if(item.getItemId() == R.id.menu_profile){
                    viewPager.setCurrentItem(1);
                }
                return true;
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.menu_chat);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.menu_profile);
                        break;
                }
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.menu_chat);

    }
}