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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class signup_tab extends Fragment {

    EditText email,pass,cpass,name,mobile;
    Button btn;
    ProgressBar pg;
    FirebaseAuth fAuth;

    float v=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       ViewGroup root=(ViewGroup)inflater.inflate(R.layout.signup_tab,container,false);

        email=root.findViewById(R.id.newemail);
        pass=root.findViewById(R.id.newpass);
        name=root.findViewById(R.id.newname);
        cpass=root.findViewById(R.id.newpass1);
        mobile=root.findViewById(R.id.newmobile);
        btn=root.findViewById(R.id.Signbtn);
        pg=root.findViewById(R.id.newprogress);
        fAuth= FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("message", "userlogin:  1 ");
               String password=pass.getText().toString().trim();
                String cpassword=cpass.getText().toString().trim();
                String email1=email.getText().toString().trim();
                String username=name.getText().toString().trim();
                String mobileno=mobile.getText().toString().trim();

                        if(username.isEmpty()){
                            name.setError("Name is required");
                            name.requestFocus();
                            return;
                        }
                        if(email1.isEmpty()){
                            email.setError("email no is required");
                            email.requestFocus();
                            return;
                        }
                        if(mobileno.isEmpty()){
                            mobile.setError("email no is required");
                            mobile.requestFocus();
                            return;
                        }
                        if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
                            email.setError("Please enter a valid Email");
                            email.requestFocus();
                            return;
                        }
                        if(password.isEmpty()){
                            pass.setError("Password is required");
                            pass.requestFocus();
                            return;
                        }
                        if(password.length()<6){
                            pass.setError("Min Password length should be 6 characters!");
                            pass.requestFocus();
                            return;
                        }
                        if(!password.equals(cpassword)) {
                            cpass.setError("Confirm Password is not same");
                            cpass.requestFocus();
                            return;
                        }
                pg.setVisibility(View.VISIBLE);

             fAuth.createUserWithEmailAndPassword(email1, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if(task.isSuccessful()){
                                    User user= new User(username,email1,mobileno);
                                    FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    pg.setVisibility(View.GONE);
                                                    Log.d("message", "userlogin: 2 success");
                                                    Intent i=new Intent(getActivity(),HomeActivity.class);
                                                    startActivity(i);
                                                    // Toast.makeText(getActivity(),"Sign up successful.",Toast.LENGTH_LONG).show();

                                                    // FirebaseAuth.getInstance().signOut();
                                                    //Fragment fragment = new login_tab();

                                                    //FragmentManager fragmentManager = getFragmentManager();
                                                    //FragmentPagerAdapter
                                                    //fragmentManager.beginTransaction().replace(R.id.view_page, fragment).commit();
                                                }
                                                else{
                                                    pg.setVisibility(View.GONE);
                                                    Toast.makeText(getActivity(),"Failed to registered!",Toast.LENGTH_LONG).show();
                                                    Log.d("message", "userlogin:3 faile ");
                                                }
                                            }
                                        });

                               }else {
                                   pg.setVisibility(View.GONE);
                                   email.setError("Email has used");
                                   Log.d("message", "userlogin: 4 failes");
                               }
                           }
                        });
            }
        });

        return root;
    }
}
