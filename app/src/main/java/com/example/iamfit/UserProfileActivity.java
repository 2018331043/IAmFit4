package com.example.iamfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {
    public TextView height,weight,steps,name,weightValue,stepsValue;
    public DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Intent intent=getIntent();
        final String user=intent.getStringExtra(ParentSearchActivity.EXTRA_USERID);
        Toast.makeText(UserProfileActivity.this, user, Toast.LENGTH_SHORT).show();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        height=findViewById(R.id.HeigthValue);
        weight=findViewById(R.id.WeightValue);
        steps=findViewById(R.id.StepsValue);
        name=findViewById(R.id.textView7);
        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String uid= snapshot.getKey();
                    if(uid.equals(user)){
                        //String name=snapshot.child("name").getValue().toString();
                        name.setText(snapshot.child("name").getValue().toString());
                        height.setText(snapshot.child("height").getValue().toString());
                        weight.setText(snapshot.child("weight").getValue().toString());
                        steps.setText(snapshot.child("stepcount").getValue().toString());
                        break;
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
