package com.example.iamfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private ImageButton button,medicinebutton,searchButton,profileButton,friendsButton;
    public TextView stepCount,distanceCount,calorieCount,bmiView;
    public DatabaseReference databaseReference;
    private Integer stepcount =0,temp=0,temp2=0,temp1=0,iter;
    double previous_step=0;
    public Calendar time;
    public Date tstart;
    public SimpleDateFormat sf;
    public String sdate;
    public Integer vals;
    String curDate;
    private static final int REQUEST_CODE2 = 102;
    private static final int REQUEST_CODE3 = 103;
    private static final int REQUEST_CODE = 101;
    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        button = findViewById(R.id.imageButtonHomeSettings);
        stepCount=findViewById(R.id.textViewSteps);
        searchButton=findViewById(R.id.imageButtonHomeSearch);
        medicinebutton=findViewById(R.id.imageButtonMedicineReminder);
        profileButton=findViewById(R.id.imageButtonProfile);
        friendsButton=findViewById(R.id.imageButtonConnection);
        bmiView=findViewById(R.id.textViewBMI);
        bmiView.setText("N/A");
        databaseReference=FirebaseDatabase.getInstance().getReference("Users");
        time=Calendar.getInstance();
        tstart=time.getTime();
        sf=new SimpleDateFormat("dd");
        sdate=sf.format(tstart);
        distanceCount=findViewById(R.id.textViewDistance);
        calorieCount=findViewById(R.id.textViewcalorie);
        temp1=0;
        temp1+=sdate.toCharArray()[1]-48;
        temp1+=10*(sdate.toCharArray()[0]-48);
        vals=0;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
        SharedPreferences sharedPreferences2=getSharedPreferences("DateSaver", Context.MODE_PRIVATE);
        curDate=sharedPreferences2.getString("curDate",null);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ParentSearchActivity.class);
                startActivity(i);
            }
        });
        medicinebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, Medicine_List_Activity.class);
                startActivity(i);
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, UserProfileActivity.class);
                startActivity(i);
            }
        });
        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, FindDoctorTestActivity.class);
                startActivity(i);
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User currentUser2 = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue(User.class);
                ArrayList<StepCount> stpc = currentUser2.getStepCounts();
                SimpleDateFormat datef;
                Date calendar = Calendar.getInstance().getTime();
                datef = new SimpleDateFormat("YYYY.MM.dd");
                String curD;
                curD = datef.format(calendar);
                iter=stpc.size()-1;
                if (!stpc.get(stpc.size() - 1).getDate().toString().equals(curD)) {
                    StepCount temp=new StepCount(curD,0);
                    stpc.add(temp);
                    databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("stepCounts").setValue(stpc);
                    stepCount.setText(currentUser2.getStepCounts().get(stpc.size() - 1).getSteps().toString());
                    SharedPreferences sharedPreferences2=getSharedPreferences("DateSaver", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor2=sharedPreferences2.edit();
                    editor2.putString("curDate",curD);
                    editor2.commit();

                } else {
                    vals=currentUser2.getStepCounts().get(stpc.size() - 1).getSteps();
                    stepCount.setText(currentUser2.getStepCounts().get(stpc.size() - 1).getSteps().toString());
                }
                Float dis = currentUser2.getStepCounts().get(stpc.size() - 1).getSteps() * .76f;
                distanceCount.setText(String.format("%.2f",dis).toString() + " m");
                Float cal =currentUser2.getStepCounts().get(stpc.size() - 1).getSteps()* .05f;
                calorieCount.setText(String.format("%.2f",cal).toString() + " cal");
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
                        if (stepdif > 5) {
                            temp++;

                        }
                        if (temp >= 1) {

                           vals++;
                            temp = 0;
                            databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("stepCounts").child(iter.toString()).child("steps").setValue(vals);
                        }
                        previous_step=val;
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
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            }
        });
    }
    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_CODE2);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},REQUEST_CODE3);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    //currentLocation=location;
                    //SupportMapFragment supportMapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById((R.id.map));
                    //supportMapFragment.getMapAsync();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
