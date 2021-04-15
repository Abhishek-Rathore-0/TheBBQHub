package com.example.thebbqhub;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        if(fAuth.getInstance().getCurrentUser()!=null){
                            Intent i = new Intent(SplashActivity.this,HomeActivity.class);
                            startActivity(i);
                        }
                        else {
                            Intent i = new Intent(SplashActivity.this, Login.class);
                            startActivity(i);
                        }
                        finish(); //no returning back
                    }
                },
                5000
        ); //closes the post delayed
    }
}