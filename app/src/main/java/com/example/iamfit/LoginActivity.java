package com.example.iamfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {
    public EditText emailid2,pass2;
    public TextView text2;
    public Button button2;
    FirebaseAuth mAuth2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailid2 =findViewById(R.id.Mail);
        pass2 =findViewById(R.id.pass);
        button2=findViewById(R.id.button);
        mAuth2= FirebaseAuth.getInstance();
        text2=findViewById(R.id.textView2);
        button2.setOnClickListener(new View.OnClickListener() {
            String email= emailid2.getText().toString();
            String pwd=pass2.getText().toString();
            @Override
            public void onClick(View view) {
                if(email.isEmpty()){
                    emailid2.setError("Please Enter a valid Email Id");
                   // emailid2.setError("mahin");
                    emailid2.requestFocus();
                }
                else if(pwd.isEmpty()){
                    pass2.setError("Please Enter a password");
                    pass2.requestFocus();
                }
                else if(!(pwd.isEmpty()&&email.isEmpty())){
                    mAuth2.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this,"SignIn Unsuccesful",Toast.LENGTH_SHORT);
                            }
                            else{
                                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(LoginActivity.this,"SignUp Unsuccesful",Toast.LENGTH_SHORT);
                }
            }
        });
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        });
    }
}
