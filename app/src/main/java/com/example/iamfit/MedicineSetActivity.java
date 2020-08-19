package com.example.iamfit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MedicineSetActivity extends AppCompatActivity {
    public EditText medicineName,timeH,timeM,daysToLast;
    public Button setReminder;
    public SimpleDateFormat sfH,sfM,sfS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_set);
        medicineName=findViewById(R.id.medicinName);
        timeH=findViewById(R.id.dosetime);
        timeM=findViewById(R.id.dosetime2);
        daysToLast=findViewById(R.id.dosetime3);
        setReminder=findViewById(R.id.button3);
        setReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String medName=medicineName.getText().toString();
                String hour=timeH.getText().toString();
                String minute=timeM.getText().toString();
                Intent intent=new Intent(MedicineSetActivity.this,ReminderBroadcast.class);
                PendingIntent pendingIntent= PendingIntent
                        .getBroadcast(MedicineSetActivity.this,0,intent,0);
                AlarmManager alarmManager= (AlarmManager)getSystemService(ALARM_SERVICE);
                Date calendar= Calendar.getInstance().getTime();
                sfH=new SimpleDateFormat("HH");
                sfM=new SimpleDateFormat("mm");
                sfS=new SimpleDateFormat("ss");
                String curH,curM,curS;
                curH=sfH.format(calendar);
                curM=sfM.format(calendar);
                curS=sfS.format(calendar);
                int cH,cM,cS,reqH,reqM,reqS,dif=0,temp;
                cH=curH.toCharArray()[1];
                cH+=curH.toCharArray()[0]*10;
                cM=curM.toCharArray()[1];
                cM+=curH.toCharArray()[0]*10;
                cS=curS.toCharArray()[1];
                cS+=curS.toCharArray()[0]*10;
                reqH=hour.toCharArray()[0];
                reqH+=10*hour.toCharArray()[1];
                reqM=minute.toCharArray()[0];
                reqM+=10*minute.toCharArray()[1];
                if(cH-reqH<0){
                    reqH+=24;
                    dif+=(reqH-cH)*60;
                }else{
                    dif+=(reqH-cH)*60;
                }
                if(cM-reqM<0){
                    reqM+=60;
                    dif+=(reqM-cM);
                }else{
                    dif+=(reqM-cM);
                }
                long now=System.currentTimeMillis()-cS*1000;
                Toast.makeText(MedicineSetActivity.this, "Hi "+dif, Toast.LENGTH_SHORT).show();
                /*alarmManager.set(AlarmManager.RTC_WAKEUP,
                        now+(dif*60*1000),
                        pendingIntent);*/
                alarmManager.set(AlarmManager.RTC_WAKEUP,
                        now+20*1000,
                        pendingIntent);
                //Log.d(TAG, "onClick: ");
            }
        });
    }

}
