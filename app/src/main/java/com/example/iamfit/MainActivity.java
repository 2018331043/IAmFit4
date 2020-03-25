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

public class MainActivity extends AppCompatActivity {
    EditText emailid,pass;
    TextView text;
    Button button;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_main);
        emailid =findViewById(R.id.Mail2);
        pass =findViewById(R.id.pass2);
        button=findViewById(R.id.button2);
        mAuth=FirebaseAuth.getInstance();
        text=findViewById(R.id.textView3);
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
                    mAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                            }
                            else{
                                Toast.makeText(MainActivity.this,"SignUp Unsuccesful 1",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(MainActivity.this,"SignUp Unsuccesful",Toast.LENGTH_SHORT).show();
                }
            }
        });
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
    }
}
