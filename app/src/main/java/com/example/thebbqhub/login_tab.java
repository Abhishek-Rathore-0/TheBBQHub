package com.example.thebbqhub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login_tab extends Fragment {

    private EditText email,pass;
    private Button btn;
    private FirebaseAuth fAuth;
    private ProgressBar pg;
    float v=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup)inflater.inflate(R.layout.login_tab,container,false);
        Login lg=new Login();
        email=(EditText) root.findViewById(R.id.email);
        pass=(EditText)  root.findViewById(R.id.pass);
        pg=root.findViewById(R.id.progress);
        btn=root.findViewById(R.id.logbtn);
        fAuth=FirebaseAuth.getInstance();

        email.setTranslationX(800);
        pass.setTranslationX(800);
        btn.setTranslationX(800);

        email.setAlpha(v);
        pass.setAlpha(v);
        btn.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        btn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userlogin();
            }
        });
        return root;
    }


    private void userlogin() {
        Log.d("message", "userlogin: 1");
        String semail=email.getText().toString().trim();
        String spass=pass.getText().toString().trim();

        if(semail.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(semail).matches()){
            email.setError("Please enter a valid Email");
            email.requestFocus();
            return;
        }

        if(spass.isEmpty()){
            pass.setError("Password is required");
            pass.requestFocus();
            return;
        }
        pg.setVisibility(View.VISIBLE);
        Log.d("message", "userlogin: 2");
        fAuth.signInWithEmailAndPassword(semail,spass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                        Log.d("message", "userlogin: 3");
                        Toast.makeText(getActivity(),"Login Successful",Toast.LENGTH_LONG).show();
                        pg.setVisibility(View.GONE);
                        Intent i=new Intent(getActivity(),HomeActivity.class);
                        startActivity(i);
                }
                else{
                    Toast.makeText(getActivity(),"Check your credentials!",Toast.LENGTH_LONG).show();
                    pg.setVisibility(View.GONE);
                    Log.d("message", "userlogin: 5");
                }

            }
        });

    }
}