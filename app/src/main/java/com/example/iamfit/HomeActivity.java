package com.example.iamfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private ImageButton button;
    public TextView stepCount;
    public DatabaseReference databaseReference;
    public User currentUser;



    //Saver saver=new Saver();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        button = findViewById(R.id.imageButton9);
        stepCount=findViewById(R.id.textViewStepsCount);
        databaseReference=FirebaseDatabase.getInstance().getReference("Users");
        //currentUser=new User("Name","Email","Height","Weight","999");
        //currentUser=new User();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User currentUser2=dataSnapshot.child(FirebaseAuth.getInstance().getUid()).getValue(User.class);
                stepCount.setText(currentUser2.getStepcount());
                Toast.makeText(HomeActivity.this,"Data Faillure",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //stepCount.setText(currentUser.getStepcount());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                SharedPreferences sharedPreferences=getSharedPreferences("LoggedInChecker", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("In","0");
                editor.commit();
               // saver.saveintopf("LoggedInChecker","In","0");
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            }
        });
    }

}
