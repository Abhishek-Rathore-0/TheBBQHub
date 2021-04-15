package com.example.thebbqhub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ContactFragment extends Fragment {
    RatingBar rb1,rb2,rb3;
    EditText comment;
    Button feed;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup)inflater.inflate(R.layout.fragment_contact,container,false);
        rb1=(RatingBar) root.findViewById(R.id.ratingfood);
        rb2=(RatingBar) root.findViewById(R.id.ratingservice);
        rb3=(RatingBar) root.findViewById(R.id.ratingdinning);
        comment=(EditText) root.findViewById(R.id.editTextMultiLine);
        feed=(Button) root.findViewById(R.id.button);

        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rb11 = rb1.getRating();
                Float rb22 = rb2.getRating();
                Float rb33 = rb3.getRating();
                String comment1 = comment.getText().toString();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                String userId = user.getUid();
                reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User userp = snapshot.getValue(User.class);
                          if (userp != null) {
                              feedback fb = new feedback(rb11, rb22, rb33, comment1, userp.name, userp.email);
                              FirebaseDatabase.getInstance().getReference("Feedback").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                      .setValue(fb).addOnCompleteListener(new OnCompleteListener<Void>() {
                                  @Override
                                  public void onComplete(@NonNull Task<Void> task) {
                                      if (task.isSuccessful()) {
                                          Toast.makeText(getActivity(), "Feedback submitted", Toast.LENGTH_LONG).show();
                                          Intent i=new Intent(getActivity(),HomeActivity.class);
                                          startActivity(i);
                                      } else {
                                          Toast.makeText(getActivity(), "Feedback did not get submitted", Toast.LENGTH_LONG).show();
                                          Log.d("message", "userlogin:3 faile ");
                                      }
                                  }
                              });
                          }
                      }
                      @Override
                      public void onCancelled(@NonNull DatabaseError error) {

                      }
                });
            }
        });
        return root;
    }
}
