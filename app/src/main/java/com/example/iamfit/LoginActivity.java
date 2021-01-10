package com.example.iamfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    public EditText emailid2,pass2;
    public TextView text2;
    public Button button2;
    private ProgressBar progressBarLogin;
    FirebaseAuth mAuth2;
    //Saver saver=new Saver();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBarLogin=(ProgressBar)findViewById(R.id.progressBarLogin);
        progressBarLogin.setVisibility(View.INVISIBLE);
        emailid2 =findViewById(R.id.Email);
        pass2 =findViewById(R.id.Password);
        button2=findViewById(R.id.LoginButton);
        mAuth2= FirebaseAuth.getInstance();
        text2=findViewById(R.id.textViewLoginSignUpMessage);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email= emailid2.getText().toString();
                String pwd=pass2.getText().toString();
                if(email.isEmpty()){
                    emailid2.setError("Please Enter a valid Email Id");
                   // emailid2.setError("mahin");
                    emailid2.requestFocus();
                }
                else if(pwd.isEmpty()){
                    pass2.setError("Please Enter a password");
                    pass2.requestFocus();
                }
                else if(!pwd.isEmpty()&&!email.isEmpty()){
                    progressBarLogin.setVisibility(View.VISIBLE);
                    mAuth2.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            String email2=emailid2.getText().toString();
                            String pwd2=pass2.getText().toString();
                            if(!task.isSuccessful()){
                                progressBarLogin.setVisibility(View.VISIBLE);
                                Toast.makeText(LoginActivity.this,"SignIn Unsuccesful",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                progressBarLogin.setVisibility(View.VISIBLE);
                                SharedPreferences sharedPreferences=getSharedPreferences("LoggedInChecker", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putString("In","1");
                                editor.commit();
                                SharedPreferences sharedPreferences2=getSharedPreferences("Username", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor2=sharedPreferences2.edit();
                                editor2.putString("Email",email2);
                                editor2.commit();
                                SharedPreferences sharedPreferences3=getSharedPreferences("Pass", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor3=sharedPreferences3.edit();
                                editor3.putString("Pass",pwd2);
                                editor3.commit();
                                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(LoginActivity.this,"SignIn 2 Unsuccesful",Toast.LENGTH_SHORT).show();
                }
            }
        });
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
