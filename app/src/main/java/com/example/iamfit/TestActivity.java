package com.example.iamfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
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
import java.util.List;

public class TestActivity extends AppCompatActivity {
    public DatabaseReference databaseReference;
    public BarChart barchart;
    public ArrayList<StepCount> stpc;
    List<BarEntry> entries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_view_test);
        barchart = (BarChart) findViewById(R.id.chart);
        entries= new ArrayList<BarEntry>();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User currentUser2 = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue(User.class);
                stpc = currentUser2.getStepCounts();
                //Toast.makeText(TestActivity.this, "Clicked "+stpc.size(), Toast.LENGTH_SHORT).show();
                ValueFormatter formatter = new ValueFormatter() {
                    @Override
                    public String getAxisLabel(float value, AxisBase axis) {

                        int s=(int)value;
                        s--;
                        //Toast.makeText(TestActivity.this, "Hello "+stpc.size()+" "+s, Toast.LENGTH_SHORT).show();
                        return stpc.get(s).getDate().toString();
                        //return "Val";
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
               // barchart.setDrawGridBackground(false);
                barchart.getAxisLeft().setDrawGridLines(false);
                barchart.getAxisRight().setDrawGridLines(false);
                barchart.getXAxis().setDrawGridLines(false);
                barchart.getDescription().setEnabled(false);
                barchart.getLegend().setEnabled(false);
                barchart.setData(data);


               // data.setBarWidth(0.9f);
                barchart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}