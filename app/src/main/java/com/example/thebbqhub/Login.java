package com.example.thebbqhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import android.os.Bundle;



import com.google.android.material.tabs.TabLayout;


public class Login extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    LoginAdapter1 adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_page);

        adapter = new LoginAdapter1(getSupportFragmentManager());
        adapter.AddFragment(new login_tab(), "Login");
        adapter.AddFragment(new signup_tab(), "Sign up");
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}