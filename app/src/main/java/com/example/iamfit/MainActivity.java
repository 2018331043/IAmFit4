package com.example.iamfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Saver saver=new Saver();
    FirebaseAuth mAuth2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_layout);
        mAuth2=FirebaseAuth.getInstance();
        SharedPreferences sharedPreferences=getSharedPreferences("LoggedInChecker", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("In")) {
            String info = sharedPreferences.getString("In", "No value");

            //Toast.makeText(MainActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
            if (info.equals("1")) {
                // Toast.makeText(MainActivity.this,"You are logged in",Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences2=getSharedPreferences("Username", Context.MODE_PRIVATE);
                SharedPreferences sharedPreferences3=getSharedPreferences("Pass", Context.MODE_PRIVATE);
                String email= sharedPreferences2.getString("Email", "No value");
                String pwd = sharedPreferences3.getString("Pass", "No value");
                mAuth2.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                                    startActivity(i);
                                }
                            }, 1000);
                        }
                        else{
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    Intent i = new Intent(MainActivity.this, SignUpActivity.class);
                                    startActivity(i);
                                }
                            }, 1000);
                        }
                    }
                });

            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
                    }
                }, 1000);
            }
        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent i = new Intent(MainActivity.this, SignUpActivity.class);
                    startActivity(i);
                }
            }, 1000);
        }
    }

}

