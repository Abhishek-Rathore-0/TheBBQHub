package com.example.thebbqhub;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class login_tab extends Fragment {

    EditText mobile;
    EditText pass;
    Button btn;
    float v=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup)inflater.inflate(R.layout.login_tab,container,false);
        Login lg=new Login();
        mobile=root.findViewById(R.id.mobile);
        pass=root.findViewById(R.id.pass);
        btn=root.findViewById(R.id.logbtn);

        mobile.setTranslationX(800);
        pass.setTranslationX(800);
        btn.setTranslationX(800);

        mobile.setAlpha(v);
        pass.setAlpha(v);
        btn.setAlpha(v);

        mobile.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        btn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),HomeActivity.class);
                startActivity(i);
            }
        });
        return root;
    }
}