package com.example.iamfit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
    public ArrayList<Medicine> mediciness=new ArrayList<Medicine>();
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
                String days=daysToLast.getText().toString();
                int length=days.length();
                int daysrem=0;
                int temp=0;
                for(int i=length-1;i>=0;i--){
                    daysrem+=(days.toCharArray()[i]-48)*Math.pow(10,temp);
                    temp++;
                }
                temp=0;
                Intent intent=new Intent(MedicineSetActivity.this,ReminderBroadcast.class);
                PendingIntent pendingIntent= PendingIntent
                        .getBroadcast(MedicineSetActivity.this,0,intent,0);
                 ArrayList <AlarmManager> alarmManagers=new ArrayList<>();
                AlarmManager alarmManager= (AlarmManager)getSystemService(ALARM_SERVICE);
                Date calendar= Calendar.getInstance().getTime();

                sfH=new SimpleDateFormat("HH");
                sfM=new SimpleDateFormat("mm");
                sfS=new SimpleDateFormat("ss");
                String curH,curM,curS;
                curH=sfH.format(calendar);
                curM=sfM.format(calendar);
                curS=sfS.format(calendar);
                int cH,cM,cS,reqH,reqM,reqS,dif=0;
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


                loadMedicineData();
                Medicine medicine =new Medicine(dif,reqH,reqM,daysrem,medName);
                mediciness.add(medicine);
                temp=1;
                while(temp==1){
                    temp=0;
                    for(int i=0;i<mediciness.size()-1;i++){
                        if(mediciness.get(i).getDif()>mediciness.get(i+1).getDif()){
                            temp=1;
                            Medicine te= mediciness.get(i);
                            mediciness.set(i,mediciness.get(i+1));
                            mediciness.set(i+1,te);
                        }
                    }
                }
                saveMedicineData();
                //ReminderBroadcast.Medicines.add(medicine);

                for(int i=0;i<mediciness.size();i++){
                    Toast.makeText(MedicineSetActivity.this, mediciness.get(i).getName(), Toast.LENGTH_LONG).show();
                }
                long now=System.currentTimeMillis()-cS*1000;
                Toast.makeText(MedicineSetActivity.this, "Hi"+dif, Toast.LENGTH_LONG).show();
                //if(mediciness.size()==1){
                    Toast.makeText(MedicineSetActivity.this, "Triggered 1", Toast.LENGTH_LONG).show();
                    alarmManager.set(AlarmManager.RTC_WAKEUP,
                            now+(dif*60*1000),
                            pendingIntent);
                //}
                //else{
                  //  Toast.makeText(MedicineSetActivity.this, "Triggered 2", Toast.LENGTH_LONG).show();
                    //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,now+mediciness.get(0).getDif()
                        //    ,
                      //      ,pendingIntent);
                //}

            }
        });
    }
    public void loadMedicineData(){
        SharedPreferences sharedPreferences =getSharedPreferences("MedicineList",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("ListOfTheMedicines",null);
        Type type=new TypeToken<ArrayList<Medicine>>() {}.getType();
        mediciness=gson.fromJson(json,type);
        if(mediciness==null){
            mediciness=new ArrayList<>();
        }
    }
    public void saveMedicineData(){
        SharedPreferences sharedPreferences =getSharedPreferences("MedicineList",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=gson.toJson(mediciness);
        editor.putString("ListOfTheMedicines",json);
        editor.apply();
    }

}
