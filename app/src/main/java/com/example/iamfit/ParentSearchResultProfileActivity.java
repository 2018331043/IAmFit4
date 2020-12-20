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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ParentSearchResultProfileActivity extends AppCompatActivity {
    public TextView height,weight,steps,name,calorieValue,disValue,ageView,bmi;
    private User searchedUser;
    public DatabaseReference databaseReference;
    private BarChart barchart;
    private ImageView addParent;
    List<BarEntry> entries;
    private ArrayList<StepCount> stpc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        Intent intent=getIntent();
        final String user=intent.getStringExtra(ParentSearchActivity.EXTRA_USERID);
        //Toast.makeText(ParentSearchResultProfileActivity.this, user, Toast.LENGTH_SHORT).show();
        barchart = (BarChart) findViewById(R.id.barChart);
        entries = new ArrayList<BarEntry>();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        height=findViewById(R.id.textView5);
        weight=findViewById(R.id.textView6);
        steps=findViewById(R.id.textView11);
        disValue=findViewById(R.id.textView10);
        calorieValue=findViewById(R.id.textView12);
        ageView=findViewById(R.id.textView13);
        bmi=findViewById(R.id.textView7);
        addParent=findViewById(R.id.imageView9);
        bmi.setText("N/A");
        name=findViewById(R.id.textView);

        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String uid= snapshot.getKey();
                    if(uid.equals(user)){
                        //String name=snapshot.child("name").getValue().toString();
                        searchedUser=snapshot.getValue(User.class);
                        /*name.setText(snapshot.child("name").getValue().toString());
                        height.setText(snapshot.child("height").getValue().toString());
                        weight.setText(snapshot.child("weight").getValue().toString());
                        steps.setText(snapshot.child("stepcount").getValue().toString());*/
                        break;
                    }


                }
                /*if(searchedUser..equals("0")){
                    Toast.makeText(ParentSearchResultProfileActivity.this, "Hello ", Toast.LENGTH_SHORT).show();
                }*/
                name.setText(searchedUser.getName());
                height.setText(searchedUser.getHeight());
                weight.setText(searchedUser.getWeight());
                stpc=searchedUser.getStepCounts();
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
                Integer age=year-searchedUser.getYear();
                ageView.setText("Age : "+age.toString());
                //steps.setText(snapshot.child("stepcount").getValue().toString());
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
        addParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //if( !databaseReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).equals("0")){


                        //}
                        //User searchUser=dataSnapshot.child()
                        User currentUser=new User();
                        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            String uidk= snapshot.getKey();
                            if(uidk.equals(user)){
                                searchedUser=snapshot.getValue(User.class);
                                break;
                            }

                        }
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            String uids= snapshot.getKey();
                            if(uids.equals(uid)){
                                currentUser=snapshot.getValue(User.class);
                                break;
                            }

                        }
                        if(currentUser.getParent().equals("0")){
                            Toast.makeText(ParentSearchResultProfileActivity.this, "Hello ", Toast.LENGTH_SHORT).show();
                            ArrayList<String> children=searchedUser.getChilds();
                            if(children.size()==1&&children.get(0).equals("0")){
                                children.clear();
                            }
                            children.add(uid);
                            databaseReference.child("Users").child(user).child("childs").setValue(children);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
