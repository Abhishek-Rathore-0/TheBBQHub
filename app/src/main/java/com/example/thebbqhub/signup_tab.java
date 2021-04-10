package com.example.thebbqhub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class signup_tab extends Fragment {

    EditText mobile;
    EditText pass;
    EditText cpass;
    EditText name;
    Button btn;

    float v=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       ViewGroup root=(ViewGroup)inflater.inflate(R.layout.signup_tab,container,false);

        mobile=root.findViewById(R.id.newmobile);
        pass=root.findViewById(R.id.newpass);
        name=root.findViewById(R.id.newname);
        cpass=root.findViewById(R.id.newpass1);
        btn=root.findViewById(R.id.Signbtn);

        mobile.setTranslationX(800);
        pass.setTranslationX(800);
        name.setTranslationX(800);
        cpass.setTranslationX(800);
        btn.setTranslationX(800);

        mobile.setAlpha(v);
        pass.setAlpha(v);
        name.setAlpha(v);
        cpass.setAlpha(v);
        btn.setAlpha(v);

        mobile.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        name.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        cpass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();
        btn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();

        return root;
    }
}
