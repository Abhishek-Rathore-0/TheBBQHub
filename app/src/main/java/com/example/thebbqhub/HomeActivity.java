package com.example.thebbqhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    TextView txt;
    ProgressBar pg;
    private FirebaseUser user;
    private DatabaseReference reference;

    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNav=findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new HomeFragment()).commit();

        txt=findViewById(R.id.welcome);
        pg= findViewById(R.id.progress);
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userId=user.getUid();

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                    User userp=snapshot.getValue(User.class);

                    if(userp !=null) {
                        String name = userp.name;
                        txt.setText("Welcome, " + name + "!");
                        pg.setVisibility(View.GONE);
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment=null;


            switch(item.getItemId())
            {
                case R.id.nav_home:
                    selectedFragment=new HomeFragment();
                    break;


                case R.id.nav_contact:
                    selectedFragment=new ContactFragment();
                    break;

                case R.id.nav_profile:
                    selectedFragment=new ProfileFragment();
                    break;


            }
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,selectedFragment).commit();
            return true;
        }

    };
}