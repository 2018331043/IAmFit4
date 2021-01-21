package com.example.iamfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {
    public TextView height,weight,steps,name,calorieValue,disValue,ageView,bmi;
    public DatabaseReference databaseReference;
    private BarChart barchart;
    private ArrayList<StepCount> stpc;
    private ImageView profilepic;
    private ImageButton imageButton;
    List<BarEntry> entries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        barchart = (BarChart) findViewById(R.id.barChart);
        entries = new ArrayList<BarEntry>();

        imageButton=(ImageButton)findViewById(R.id.imageButton6);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Toast.makeText(UserProfileActivity.this, user, Toast.LENGTH_SHORT).show();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        height=findViewById(R.id.textView5);
        weight=findViewById(R.id.textView6);
        steps=findViewById(R.id.textView11);
        name=findViewById(R.id.textView);
        calorieValue=findViewById(R.id.textView12);
        disValue=findViewById(R.id.textView10);
        ageView=findViewById(R.id.textView13);
        bmi=findViewById(R.id.textView7);
        profilepic=findViewById(R.id.profile_image_recyclerview_child);
        bmi.setText("N/A");
        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                   // String uid= snapshot.getKey();
                   // if(uid.equals(user)){
                        //String name=snapshot.child("name").getValue().toString();
                         User currentUser2 = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue(User.class);
                         stpc = currentUser2.getStepCounts();
                        if(!currentUser2.getImageurl().toString().equals("0")){
                            Picasso.get().load(currentUser2.getImageurl()).into(profilepic);
                        }
                        name.setText(currentUser2.getName().toString());
                        height.setText(currentUser2.getHeight().toString());
                        weight.setText(currentUser2.getWeight().toString());
                        double total_dis;
                        double total_cal1;
                        Integer total_steps=0,total_cal2;
                        for(int i=0;i<stpc.size();i++){
                            total_steps+=stpc.get(i).getSteps();
                        }
                        steps.setText(total_steps.toString()+" steps");
                        total_cal1=total_steps*.05;
                        total_dis=total_steps*.76;
                        disValue.setText(String.format("%.2f",total_dis)+" m");
                        calorieValue.setText(String.format("%.2f",total_cal1)+" cal");
                        Calendar calender =Calendar.getInstance();
                        final int year=calender.get(Calendar.YEAR);
                        Integer age=year-currentUser2.getYear();
                        ageView.setText("Age : "+age.toString());
                ValueFormatter formatter = new ValueFormatter() {
                    @Override
                    public String getAxisLabel(float value, AxisBase axis) {

                        int s=(int)value;
                        s--;
                        //Toast.makeText(TestActivity.this, "Hello "+stpc.size()+" "+s, Toast.LENGTH_SHORT).show();
                        String temp=stpc.get(s).getDate().toString();
                        String temp2="7890";
                        int j=0;

                        return stpc.get(s).getDate().toString();

                    }
                };
                XAxis xAxis = barchart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
                xAxis.setValueFormatter(formatter);
                YAxis yAxis=barchart.getAxisRight();
                yAxis.setDrawLabels(false);
                for(int i=0;i<stpc.size();i++){
                    entries.add(new BarEntry((float)(i+1),stpc.get(i).getSteps()));
                }

                BarDataSet set = new BarDataSet(entries, "Steps");
                set.setColor(R.color.color3);
                BarData data = new BarData(set);
                barchart.animate();
                barchart.animateXY(900,1200);
                // barchart.setDrawGridBackground(false);
                barchart.getAxisLeft().setDrawGridLines(false);
                barchart.getAxisRight().setDrawGridLines(false);
                barchart.getXAxis().setDrawGridLines(false);
                barchart.getDescription().setEnabled(false);
                barchart.getLegend().setEnabled(false);
                barchart.setFitBars(true);
                barchart.setPinchZoom(false);
                //barchart.setDrawBarShadow(true);
                data.setBarWidth(0.9f);
                barchart.setData(data);


                // data.setBarWidth(0.9f);
                barchart.invalidate();

                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*Integer i=0;
        for(StepCount x:stpc){

            entries.add(new BarEntry(i,x.getSteps()));
            i++;
        }
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return stpc.get((int)value).getDate().toString();
            }
        };
        BarDataSet set = new BarDataSet(entries, "BarDataSet");

        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width
        barchart.setData(data);
        barchart.setFitBars(true); // make the x-axis fit exactly all bars
        barchart.invalidate(); // refresh
        XAxis xAxis = barchart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);*/
    }
}
