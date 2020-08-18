package com.example.iamfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private ImageButton button,searchButton;
    public TextView stepCount,distanceCount,calorieCount;
    public DatabaseReference databaseReference;
    public User currentUser;
    private Integer stepcount =0,temp=0,temp2=0,temp1=0;
    double previous_step=0;
    public TextView textView;
    public TextView textView0,textView1,textView2,dates;
    public Calendar time;
    public Date tstart;
    public SimpleDateFormat sf;
    public String sdate;
    public Integer dat[]=new Integer[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        button = findViewById(R.id.imageButton9);
        stepCount=findViewById(R.id.textViewStepsCount);
        searchButton=findViewById(R.id.imageButton12);
        databaseReference=FirebaseDatabase.getInstance().getReference("Users");
        time=Calendar.getInstance();
        tstart=time.getTime();
        sf=new SimpleDateFormat("dd");
        sdate=sf.format(tstart);
        textView = findViewById(R.id.textViewStepsCount);
        distanceCount=findViewById(R.id.textViewDistanceCount);
        calorieCount=findViewById(R.id.textViewCalorieCount);
        temp1=0;
        temp1+=sdate.toCharArray()[1]-48;
        temp1+=10*(sdate.toCharArray()[0]-48);
        dat[temp1]=0;
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ParentSearchActivity.class);
                startActivity(i);
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User currentUser2=dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue(User.class);

                dat[temp1]=currentUser2.getStepcount();
                stepCount.setText(dat[temp1].toString());
                Float dis=dat[temp1]*.76f;
                distanceCount.setText(dis.toString()+"m");
                Float cal=dat[temp1]*.05f;
                calorieCount.setText(cal.toString()+"cal");
                //Toast.makeText(HomeActivity.this,"Data Faillure",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        SensorManager sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);
        final Sensor sensor =sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        final SensorEventListener stepCounter=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                time=Calendar.getInstance();
                tstart=time.getTime();
                sf=new SimpleDateFormat("dd");
                sdate=sf.format(tstart);
                //dates=findViewById(R.id.textView4);
                //dates.setText(sdate);
                temp1=0;
                temp1+=sdate.toCharArray()[1]-48;
                temp1+=10*(sdate.toCharArray()[0]-48);
                final float alpha = 0.8f;

                // Isolate the force of gravity with the low-pass filter.
                float gravity[]=new float[100];
                float linear_acceleration[]=new float[100];
                gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
                gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
                gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

                // Remove the gravity contribution with the high-pass filter.
                linear_acceleration[0] = event.values[0] - gravity[0];
                linear_acceleration[1] = event.values[1] - gravity[1];
                linear_acceleration[2] = event.values[2] - gravity[2];
                if(event!=null){

                    Integer x1=(int)linear_acceleration[0];
                    Integer y1=(int)linear_acceleration[1];
                    Integer z1=(int)linear_acceleration[2];
                    float x= linear_acceleration[0];
                    float y= linear_acceleration[1];
                    float z= linear_acceleration[2];
                    double val=Math.sqrt(Math.abs((x*x)+(y*y)+(z*z)-50));
                    temp2++;
                    if(temp2>=10) {
                        double stepdif=val-previous_step;
                        if (stepdif > 6) {
                            temp++;

                        }
                        if (temp >= 1) {

                            dat[temp1]++;
                            temp = 0;
                            databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("stepcount").setValue(dat[temp1]);
                        }
                        previous_step=val;
                        //textView.setText(dat[temp1].toString());

                        temp2=0;
                    }

                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(stepCounter,sensor,SensorManager.SENSOR_DELAY_NORMAL);



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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
