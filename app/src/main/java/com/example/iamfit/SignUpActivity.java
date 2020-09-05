package com.example.iamfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {
    EditText emailid,pass,name,dateOfbirth,height,weight,gender;
    TextView text;
    Button button;
    private ImageButton c1;
    private TextView c2;
    DatePickerDialog.OnDateSetListener setListener;
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

        c2=(TextView) findViewById(R.id.signUpTextBirthday);
        c1=(ImageButton)findViewById(R.id.signUpPageCalender);

        Calendar calender =Calendar.getInstance();

        final int year=calender.get(Calendar.YEAR);
        final int month=calender.get(Calendar.MONTH);
        final int day=calender.get(Calendar.DAY_OF_MONTH);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog =new DatePickerDialog(MainActivity.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth,setListener,year,month,day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1=i1+1;
                String date=i2+"."+i1+"."+i;
                c2.setText(date);
            }
        };

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
                                User info =new User(name1,email,height1,weight1,0);
                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        String email2= emailid.getText().toString();
                                        String pwd2=pass.getText().toString();
                                        SharedPreferences sharedPreferences2=getSharedPreferences("Username", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor2=sharedPreferences2.edit();
                                        editor2.putString("Email",email2);
                                        editor2.commit();
                                        SharedPreferences sharedPreferences3=getSharedPreferences("Pass", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor3=sharedPreferences3.edit();
                                        editor3.putString("Pass",pwd2);
                                        editor3.commit();
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

