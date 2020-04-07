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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    EditText emailid,pass,name,dateOfbirth,height,weight,gender;
    TextView text;
    Button button;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        emailid =findViewById(R.id.email);
        pass =findViewById(R.id.password);
        button=findViewById(R.id.buttonSignup);
        name=findViewById(R.id.name);
        height=findViewById(R.id.height);
        weight=findViewById(R.id.weight);
        gender=findViewById(R.id.gender);
        mAuth= FirebaseAuth.getInstance();
        text=findViewById(R.id.textView3);
        databaseReference=FirebaseDatabase.getInstance().getReference("Users");
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email= emailid.getText().toString();
                String pwd=pass.getText().toString();
                if(email.isEmpty()){
                    emailid.setError("Please Enter A valid email Id");
                    emailid.requestFocus();
                }
                else if(pwd.isEmpty()){
                    pass.setError("Please Enter a password");
                    pass.requestFocus();
                }
                else if(!pwd.isEmpty()&&!email.isEmpty()){
                    mAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                SharedPreferences sharedPreferences=getSharedPreferences("LoggedInChecker", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putString("In","1");
                                editor.commit();
                               // databaseReference=FirebaseDatabase.getInstance().getReference();
                                String email= emailid.getText().toString();
                                String gender1=gender.getText().toString();
                                String weight1= weight.getText().toString();
                                String height1=height.getText().toString();
                                String name1=name.getText().toString();
                                User info =new User(name1,email,height1,weight1);
                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        startActivity(new Intent(SignUpActivity.this,HomeActivity.class));
                                    }
                                });

                            }
                            else{
                                Toast.makeText(SignUpActivity.this,"SignUp Unsuccesful",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(SignUpActivity.this,"SignUp Unsuccesful",Toast.LENGTH_SHORT).show();
                }
            }
        });
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            }
        });
    }
    }

