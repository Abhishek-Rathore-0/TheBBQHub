package com.example.thebbqhub;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class signup_tab extends Fragment {

    EditText email;
    EditText pass;
    EditText cpass;
    EditText name;
    Button btn;
    FirebaseAuth fAuth;

    float v=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       ViewGroup root=(ViewGroup)inflater.inflate(R.layout.signup_tab,container,false);

        email=root.findViewById(R.id.newemail);
        pass=root.findViewById(R.id.newpass);
        name=root.findViewById(R.id.newname);
        cpass=root.findViewById(R.id.newpass1);
        btn=root.findViewById(R.id.Signbtn);
        fAuth= FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("message", "userlogin:  1 ");
                Toast.makeText(getActivity(),"CLidkes Failed to registered!",Toast.LENGTH_LONG).show();
                String password=pass.getText().toString().trim();
                String cpassword=cpass.getText().toString().trim();
                String email1=email.getText().toString().trim();
                String username=name.getText().toString().trim();

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
                        if(!password.equals(cpassword)){
                            cpass.setError("Confirm Password is not same");
                            cpass.requestFocus();
                            return;
                        }
                 fAuth.createUserWithEmailAndPassword(email1, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if(task.isSuccessful()){
                                    User user= new User(username,email1);
                                    FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Log.d("message", "userlogin: 2 success");
                                                    Toast.makeText(getActivity(),"User has been registered successfully",Toast.LENGTH_LONG).show();
                                                    name.setText("");
                                                    pass.setText("");
                                                    cpass.setText("");
                                                    email.setText("");
                                                }
                                                else{
                                                    Toast.makeText(getActivity(),"Failed to registered!",Toast.LENGTH_LONG).show();
                                                    Log.d("message", "userlogin:3 faile ");
                                                }
                                            }
                                        });

                               }else {
                                   Log.d("message", "userlogin: 4 failes");
                               }
                           }
                        });
            }
        });

        return root;
    }
}
