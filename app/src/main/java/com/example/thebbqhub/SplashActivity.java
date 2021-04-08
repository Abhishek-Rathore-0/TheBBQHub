package com.example.thebbqhub;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(SplashActivity.this,HomeActivity.class);
                        startActivity(i);
                        finish(); //no returning back
                    }
                },
                5000
        ); //closes the post delayed
    }
}