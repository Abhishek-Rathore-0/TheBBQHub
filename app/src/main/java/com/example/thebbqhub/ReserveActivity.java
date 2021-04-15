package com.example.thebbqhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReserveActivity extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference reference;
    private Spinner dropdown,dropdown2,dropdown3;
    Button reserve;
    TextView head;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        head=findViewById(R.id.head);
        reserve=findViewById(R.id.reserve);
        dropdown = findViewById(R.id.spinnerno);
        dropdown2 = findViewById(R.id.spinnerslot);
        dropdown3 = findViewById(R.id.spinnerdate);

        String[] items = new String[]{"1", "2", "3","4","5","6"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        String[] items2 = new String[]{"12 Noon","3:00 PM","6:00 PM"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        dropdown2.setAdapter(adapter2);


        String[] items3 = new String[]{"Today","Tomorrow","Day After Tomorrow"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items3);
        dropdown3.setAdapter(adapter3);

        display();
        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reserve();
            }
        });
    }

    private void Reserve() {
        String  no_of_guest=dropdown.getSelectedItem().toString();
        String slot=dropdown2.getSelectedItem().toString();
        String date=dropdown3.getSelectedItem().toString();
        Calendar cd=Calendar.getInstance();
        switch (date){
            case "Tomorrow":cd.add(Calendar.DATE,1);break;
            case "Day After Tomorrow":cd.add(Calendar.DATE,2);break;
        }

        String date1=DateFormat.getDateInstance().format(cd.getTime());


        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        String userId=user.getUid();

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userp=snapshot.getValue(User.class);
                if(userp !=null) {
                      Reservation rs=new Reservation(userp.name,userp.email,userp.mobile,no_of_guest,slot,date1);
                      FirebaseDatabase.getInstance().getReference("Reservation").child(userp.mobile)
                            .setValue(rs).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Reserved Successful", Toast.LENGTH_LONG).show();
                                Intent i=new Intent(getApplicationContext(),HomeActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), "Reservation failed", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void display() {
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        String userId=user.getUid();

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                User userp=snapshot.getValue(User.class);

                if(userp !=null) {
                    head.setText("Reserving for " + userp.name );
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}