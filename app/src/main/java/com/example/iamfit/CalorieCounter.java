package com.example.iamfit;
//It is the calorie counter activity that counts the amount of callories one should take everyday to lose a certain amount of weight in one month
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class CalorieCounter extends AppCompatActivity {
    private ImageButton imageButton;
    private TextView weightResult,graphResult;
    private EditText weightInput,calorieInput;
    private DatabaseReference databaseReference;
    private Button calculate;
    public User currentUser;
    private String weightInputed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_counter);
        imageButton=(ImageButton)findViewById(R.id.imageButton4);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        weightResult=findViewById(R.id.textView47);
        weightInput=findViewById(R.id.weightInput);
        calculate=findViewById(R.id.button6);
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightInputed=weightInput.getText().toString();
                if(weightInput.getText().toString().isEmpty()){
                    weightInput.requestFocus();
                }
                else{
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        String weightInputed2=weightInputed;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            currentUser=dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue(User.class);


                            int weight=0,temp;
                            temp=0;
                            for(int i=weightInputed.length()-1;i>=0;i--){
                                weight +=(weightInputed.toCharArray()[i]-48) * Math.pow(10,temp);
                                temp++;
                            }
                            //Toast.makeText(CalorieCounter.this, "Clicked " + weight, Toast.LENGTH_SHORT).show();
                            float caloriesReqToBurn=weight*7777.78f;
                            float caloriesWillBeBurnedViaSteps=currentUser.getCurrentStepGoal()*.05f*30;
                            float mBR;
                            int userWeight=0;
                            temp=0;
                            for(int i=currentUser.getWeight().length()-1;i>=0;i--){
                                userWeight +=(currentUser.getWeight().toCharArray()[i]-48) * Math.pow(10,temp);
                                temp++;
                            }
                            float userWeightInPound=userWeight/(.45f);
                            int heightOfUSer=0;
                            int i;
                            for(i=0;i<currentUser.getHeight().length();i++){
                                if(currentUser.getHeight().toCharArray()[i]=='.'){
                                    break;
                                }
                            }
                            temp=0;
                            for(int j=i-1;j>=0;j--){
                                heightOfUSer+=(currentUser.getHeight().toCharArray()[j]-48) * Math.pow(10,temp);
                            }
                            heightOfUSer=heightOfUSer*12;
                            for(int j=currentUser.getHeight().length()-1;j>i;j--){
                                heightOfUSer+=(currentUser.getHeight().toCharArray()[j]-48) * Math.pow(10,temp);
                            }
                            Calendar calender =Calendar.getInstance();
                            final int year=calender.get(Calendar.YEAR);
                            Integer age=year-currentUser.getYear();
                            Toast.makeText(CalorieCounter.this, "Clicked " + heightOfUSer, Toast.LENGTH_SHORT).show();
                            if(currentUser.getGender().equals("Male")){
                                mBR=66+(6.23f*userWeightInPound)+(12.7f*heightOfUSer)-(6.8f*age);
                            }
                            else{
                                mBR=655+(4.35f*userWeightInPound)+(4.7f*heightOfUSer)-(4.7f*age);
                            }
                            float caloriesBurnedViaMBR=mBR*30;
                            float totalCaloriesBurned=caloriesBurnedViaMBR+caloriesWillBeBurnedViaSteps;
                            float predictedDecreamentOfWeight=totalCaloriesBurned/7777.78f;
                            float pDOWInRoundFigure= (float) Math.ceil(predictedDecreamentOfWeight);
                            //Toast.makeText(CalorieCounter.this, "Clicked " + pDOWInRoundFigure, Toast.LENGTH_SHORT).show();
                            if(predictedDecreamentOfWeight<weight){
                                weightResult.setText("It is not possible to lose that much weight in 30 days");
                            }
                            else{
                                float weightToLoseDaily=(predictedDecreamentOfWeight-weight)/30;
                                float caloriesCanBeTakenEveryDay=weightToLoseDaily*7777.78f;
                                weightResult.setText("If you complete you Current Step goal for the next 30 days and take around "+String.format("%.2f",caloriesCanBeTakenEveryDay)+"calories everyday.Then you can achieve your goal");
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }
        });

    }

}