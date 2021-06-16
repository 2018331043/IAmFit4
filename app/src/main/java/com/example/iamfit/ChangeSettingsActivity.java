package com.example.iamfit;
/*
* An activity to let the user change his/her information throuch a form
* */
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeSettingsActivity extends AppCompatActivity {
    private TextView name,heightF,heightI,weight;
    private Button doneButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_settings);
        name=findViewById(R.id.editTextTextPersonName4);
        heightF=findViewById(R.id.editTextTextPersonName5);
        heightI=findViewById(R.id.editTextNumber);
        weight=findViewById(R.id.editTextNumber2);
        doneButton=findViewById(R.id.button5);
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().isEmpty()){
                    databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").setValue(name.getText().toString());
                }
                if(!heightF.getText().toString().isEmpty()){
                    databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("height").setValue(heightF+"."+heightI);
                }
                if(!weight.getText().toString().isEmpty()){
                    databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("weight").setValue(weight.getText().toString());
                }
            }
        });
    }
}