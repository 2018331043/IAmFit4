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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MedicineSetActivity extends AppCompatActivity {
    public EditText medicineName,timeH,timeM,daysToLast;
    public static String MEDICINE_NAME="com.example.iamfit.MedicineName";

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

                //MedicineNames.set(medicineNumber,medName);
                ReminderBroadcast.MedicineNames.add(medName);
                //intent.putExtra(MEDICINE_NAME,medName);
               // intent.putExtra(MedicineNames.get(medicineNumber),medName);
                //intent.putExtra(medicineNumber,medicineNumber);
                PendingIntent pendingIntent= PendingIntent
                        .getBroadcast(MedicineSetActivity.this,0,intent,0);
                 ArrayList <AlarmManager> alarmManagers=new ArrayList<>();

                 //alarmManagers.set(medicineNumber,(AlarmManager)getSystemService(ALARM_SERVICE));
                //alarmManagers.add((AlarmManager)getSystemService(ALARM_SERVICE));
                Toast.makeText(MedicineSetActivity.this, "Hi", Toast.LENGTH_LONG).show();
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
                cH=(curH.toCharArray()[1]-48);
                cH+=(curH.toCharArray()[0]-48)*10;
                cM=curM.toCharArray()[1]-48;
                cM+=(curM.toCharArray()[0]-48)*10;
                cS=curS.toCharArray()[1]-48;
                cS+=(curS.toCharArray()[0]-48)*10;
                reqH=(hour.toCharArray()[1]-48);
                reqH+=10*(hour.toCharArray()[0]-48);
                reqM=minute.toCharArray()[1]-48;
                reqM+=10*(minute.toCharArray()[0]-48);
                if(reqH-cH<0){
                    reqH+=24;
                    dif+=(reqH-cH)*60;
                }else{
                    dif+=(reqH-cH)*60;
                }
                    dif+=(reqM-cM);

                long now=System.currentTimeMillis()-cS*1000;
                //Toast.makeText(MedicineSetActivity.this, "Hi"+" "+cH+" "+cM+" "+reqH+" "+reqM+" "+hour+" "+minute, Toast.LENGTH_LONG).show();
                Toast.makeText(MedicineSetActivity.this, "Hi"+dif, Toast.LENGTH_LONG).show();
                alarmManager.set(AlarmManager.RTC_WAKEUP,
                        now+(dif*60*1000),
                        pendingIntent);
                //alarmManager.
               // medicineNumber++;
               // alarmManagerNumbers++;
            }
        });
    }

}
