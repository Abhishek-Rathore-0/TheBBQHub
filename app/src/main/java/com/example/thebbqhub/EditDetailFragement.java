package com.example.thebbqhub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditDetailFragement extends Fragment {

    TextView txt,txte,txtm;
    Button btn1,btne;

    private FirebaseUser user;
    private String userId;
    private DatabaseReference reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup)inflater.inflate(R.layout.fragment_edit,container,false);
        txt=root.findViewById(R.id.detail);
        txte=root.findViewById(R.id.detaile);
        txtm=root.findViewById(R.id.detailm);
        btn1=root.findViewById(R.id.btn);
        btne=root.findViewById(R.id.btnchange);

        btne=root.findViewById(R.id.btnchange);
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userId=user.getUid();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i=new Intent(getActivity(),Login.class);
                startActivity(i);
            }
        });

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                User userp=snapshot.getValue(User.class);

                if(userp !=null) {
                    txt.setText("" + userp.name);
                    txte.setText(userp.email);
                    txtm.setText(userp.mobile);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        btne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onupdate();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ProfileFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flFragment, fragment).addToBackStack(null).commit();
            }
        });
        return root;
    }

    private void onupdate() {
        String email1=txte.getText().toString().trim();
        String username=txt.getText().toString().trim();
        String mobileno=txtm.getText().toString().trim();
        if(username.isEmpty()){
            txt.setError("Name is required");
            txt.requestFocus();
            return;
        }
        if(email1.isEmpty()){
            txte.setError("email no is required");
            txte.requestFocus();
            return;
        }
        if(mobileno.isEmpty()){
            txtm.setError("email no is required");
            txtm.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
            txte.setError("Please enter a valid Email");
            txte.requestFocus();
            return;
        }
        User updatesd=new User(username,email1,mobileno);
        reference.child(userId).setValue(updatesd).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d("message", "userlogin: 2 success");
                    Intent i=new Intent(getActivity(),HomeActivity.class);
                    startActivity(i);
                }

            }
        });

    }
}
