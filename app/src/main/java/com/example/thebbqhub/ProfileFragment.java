package com.example.thebbqhub;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    TextView txt,txte,txtm;
    Button btn1,btne;
    ProgressBar pg;
    private FirebaseUser user;
    private String userId;
    private DatabaseReference reference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup)inflater.inflate(R.layout.fragment_profile,container,false);
        txt=root.findViewById(R.id.detail);
        txte=root.findViewById(R.id.detaile);
        txtm=root.findViewById(R.id.detailm);
        btn1=root.findViewById(R.id.btn);
        btne=root.findViewById(R.id.btnchange);
        pg=root.findViewById(R.id.progressBar);
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
        pg.setVisibility(View.VISIBLE);
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userp=snapshot.getValue(User.class);
                if(userp !=null) {
                    txt.setText("" + userp.name);
                    txte.setText(userp.email);
                    txtm.setText(userp.mobile);
                    pg.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pg.setVisibility(View.GONE);
            }

        });

        btne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onchangedata();
            }
        });
        return root;
    }
    private void onchangedata() {
        Fragment fragment = new EditDetailFragement();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flFragment, fragment).commit();
    }

}
