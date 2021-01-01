package com.example.iamfit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MedicineSetActivity extends AppCompatActivity {
    public EditText medicineName,daysToLast;
    public static String MEDICINE_NAME="com.example.iamfit.MedicineName";
    public static String ITERATOR="com.example.iamfit.MedicineName";
    //public static String INTER="1";
    private ArrayList<Medicine> mediciness=new ArrayList<Medicine>();
    public Button setReminder;
    public SimpleDateFormat sfH,sfM,sfS;
    private Button time1;
    //private EditText time1Set;

    private RecyclerView recyclerViewTimeShow;
    int timeHour,timeMinute;
    CheckBox stomach;
    ImageButton backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_set);
        //implementing time picker dialogue

        time1=(Button) findViewById(R.id.MedicinePageTimeButton);
        //time1Set=(EditText)findViewById(R.id.MedicinePageTime);
        backButton=findViewById(R.id.MedicineButtonBack);
        stomach=findViewById(R.id.MedicinepageCheckBox);



        //time set recycler view implementation

        recyclerViewTimeShow=(RecyclerView)findViewById(R.id.recyclerViewTimeDisplay);
        LinearLayoutManager linearLayoutManagerTimeShow=new LinearLayoutManager(this);
        linearLayoutManagerTimeShow.setOrientation(RecyclerView.VERTICAL);
        recyclerViewTimeShow.setLayoutManager(linearLayoutManagerTimeShow);
        List<TimeShowModelClass> modelClassListTimeShow=new ArrayList<>();
        modelClassListTimeShow.add(new TimeShowModelClass("11:10 PM"));
        modelClassListTimeShow.add(new TimeShowModelClass("02:10 PM"));
        modelClassListTimeShow.add(new TimeShowModelClass("11:10 PM"));
        modelClassListTimeShow.add(new TimeShowModelClass("02:10 PM"));
        modelClassListTimeShow.add(new TimeShowModelClass("11:10 PM"));
        modelClassListTimeShow.add(new TimeShowModelClass("02:10 PM"));
        TimeShowAdaptar timeShowAdaptar=new TimeShowAdaptar(modelClassListTimeShow);
        recyclerViewTimeShow.setAdapter(timeShowAdaptar);
        timeShowAdaptar.notifyDataSetChanged();



        time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TimePickerDialog timePickerDialog=new TimePickerDialog(MedicineSetActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        timeHour=i;
                        timeMinute=i1;
                        Calendar calendar=Calendar.getInstance();
                        calendar.set(0,0,0,timeHour,timeMinute);
                        //time1Set.setText(DateFormat.format("hh:mm aa",calendar));
                        }
                    },12,0,false
                );
                timePickerDialog.updateTime(timeHour,timeMinute);
                timePickerDialog.show();
            }
        });
        //implementation done
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                /*Intent i = new Intent(MedicineSetActivity.this, Medicine_List_Activity.class);
                startActivity(i);*/
            }
        });
        medicineName=findViewById(R.id.editTextTextPersonName2);
       // timeH=findViewById(R.id.dosetime);
        //timeM=findViewById(R.id.dosetime2);
        daysToLast=findViewById(R.id.MedinePageDays);
        setReminder=findViewById(R.id.medicinePageReminderButton);
       /* SharedPreferences sharedPreferences =getSharedPreferences("MedicineList",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=gson.toJson(mediciness);
        editor.putString("ListOfTheMedicines",json);
        editor.apply();*/

      /* SharedPreferences sharedPreferences =getSharedPreferences("MedicineIndex",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("Index",1);
        editor.apply();*/
        setReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String medName = medicineName.getText().toString();
               // String hour = timeH.getText().toString();
               // String minute = timeM.getText().toString();
               // Toast.makeText(MedicineSetActivity.this, timeHour, Toast.LENGTH_LONG).show();
                String days = daysToLast.getText().toString();
               /* SharedPreferences sharedPreferences =getSharedPreferences("MedicineIterator",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("Iterator",0);
                editor.apply();*/
                int length = days.length();
                int daysrem = 0;
                int temp = 0;
                for (int i = length - 1; i >= 0; i--) {
                    daysrem += (days.toCharArray()[i] - 48) * Math.pow(10, temp);
                    temp++;
                }
                temp = 0;






                //ArrayList<AlarmManager> alarmManagers = new ArrayList<>();

                Date calendar = Calendar.getInstance().getTime();

                sfH = new SimpleDateFormat("HH");
                sfM = new SimpleDateFormat("mm");
                sfS = new SimpleDateFormat("ss");
                String curH, curM, curS;
                curH = sfH.format(calendar);
                curM = sfM.format(calendar);
                curS = sfS.format(calendar);
                int cH, cM, cS, reqH, reqM, reqS, dif = 0;
                cH = (curH.toCharArray()[1] - 48);
                cH += (curH.toCharArray()[0] - 48) * 10;
                cM = curM.toCharArray()[1] - 48;
                cM += (curM.toCharArray()[0] - 48) * 10;
                cS = curS.toCharArray()[1] - 48;
                cS += (curS.toCharArray()[0] - 48) * 10;
               // reqH = (hour.toCharArray()[1] - 48);
                //reqH += 10 * (hour.toCharArray()[0] - 48);
               // reqM = minute.toCharArray()[1] - 48;
               // reqM += 10 * (minute.toCharArray()[0] - 48);
                if (timeHour - cH < 0) {
                    timeHour += 24;
                    dif += (timeHour - cH) * 60;
                } else {
                    dif += (timeHour - cH) * 60;
                }
                dif += (timeMinute - cM);
                loadMedicineData();
                Integer checkBoxInt;
                if(stomach.isChecked()){
                    checkBoxInt=1;
                }
                else{
                    checkBoxInt=0;
                }
                Integer ts=loadAndRetrieveIndex();
                Medicine medicine = new Medicine(dif, timeHour, timeMinute, daysrem, medName,checkBoxInt,true,ts);
                mediciness.add(medicine);
                /*temp = 1;
                while (temp == 1) {
                    temp = 0;
                    for (int i = 0; i < mediciness.size() - 1; i++) {
                        if (mediciness.get(i).getDif() > mediciness.get(i + 1).getDif()) {
                            temp = 1;
                            Medicine te = mediciness.get(i);
                            mediciness.set(i, mediciness.get(i + 1));
                            mediciness.set(i + 1, te);
                        }
                    }
                }*/
                saveMedicineData();
                /*for (int i = 0; i < mediciness.size(); i++) {
                    Toast.makeText(MedicineSetActivity.this, "found "+mediciness.get(i).getDif(), Toast.LENGTH_LONG).show();
                }*/
                long now = System.currentTimeMillis() - cS * 1000;
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(MedicineSetActivity.this, ReminderBroadcast.class);
                intent.putExtra("medicine name",medName);
                intent.putExtra("Iterator",ts.toString());
                PendingIntent pendingIntent = PendingIntent
                        .getBroadcast(MedicineSetActivity.this, medicine.getIndex(), intent, 0);
                alarmManager.set(AlarmManager.RTC_WAKEUP,now+medicine.getDif()*60*1000
                        ,pendingIntent);
                Toast.makeText(MedicineSetActivity.this, "Medicine Reminder Set Index No" +medicine.getIndex(), Toast.LENGTH_LONG).show();
               // Toast.makeText(MedicineSetActivity.this, "Hi" + dif, Toast.LENGTH_LONG).show();

                /*for (int i = 0; i < mediciness.size(); i++) {
                    //Toast.makeText(MedicineSetActivity.this, "Hi" + i+1, Toast.LENGTH_LONG).show();
                    if ( mediciness.get(i).getHour() - cH < 0) {
                        //reqH += 24;
                       // Toast.makeText(MedicineSetActivity.this, "Debuging "+mediciness.get(i).getDif(), Toast.LENGTH_LONG).show();
                       mediciness.get(i).setDif((mediciness.get(i).getHour()+24 - cH) * 60);
                    }
                    else if(mediciness.get(i).getHour() - cH==0 && mediciness.get(i).getMinute()-cM<=0){
                        mediciness.get(i).setDif((mediciness.get(i).getHour()+24 - cH) * 60);
                       // mediciness.get(i).setDif(mediciness.get(i).getDif()+(mediciness.get(i).getMinute() - cM));
                    }
                    else {
                        mediciness.get(i).setDif((mediciness.get(i).getHour() - cH) * 60);
                    }

                    mediciness.get(i).setDif(mediciness.get(i).getDif()+(mediciness.get(i).getMinute() - cM));

                }*/

                /*temp = 1;
                while (temp == 1) {
                    temp = 0;
                    for (int i = 0; i < mediciness.size() - 1; i++) {
                        if (mediciness.get(i).getDif() > mediciness.get(i + 1).getDif()) {
                            temp = 1;
                            Medicine te = mediciness.get(i);
                            mediciness.set(i, mediciness.get(i + 1));
                            mediciness.set(i + 1, te);
                        }
                    }
                }*/
                saveMedicineData();
               /* for (int i = 0; i < mediciness.size(); i++) {
                    //Toast.makeText(MedicineSetActivity.this, "Hi" + i+1, Toast.LENGTH_LONG).show();

                    PendingIntent pendingIntent = PendingIntent
                            .getBroadcast(MedicineSetActivity.this, mediciness.get(i).getIndex(), intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    // if(mediciness.get(i).getDif()<0)continue;
                    Toast.makeText(MedicineSetActivity.this, "Hi " +i+" "+mediciness.get(i).getDif(), Toast.LENGTH_LONG).show();
                    alarmManagers.add(alarmManager);
                    alarmManagers.get(i).set(AlarmManager.RTC_WAKEUP,now+mediciness.get(i).getDif()*60*1000
                            ,pendingIntent);
                }
                saveMedicineData();*/
            }
        });
    }
    private void loadMedicineData(){
        mediciness.clear();
        SharedPreferences sharedPreferences =getSharedPreferences("MedicineList",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("ListOfTheMedicines",null);
        Type type=new TypeToken<ArrayList<Medicine>>() {}.getType();
        mediciness=gson.fromJson(json,type);
        if(mediciness==null){
            mediciness=new ArrayList<>();
        }
    }
    private void saveMedicineData(){
        SharedPreferences sharedPreferences =getSharedPreferences("MedicineList",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=gson.toJson(mediciness);
        editor.putString("ListOfTheMedicines",json);
        editor.apply();
    }
    private Integer loadAndRetrieveIndex(){
        SharedPreferences sharedPreferences =getSharedPreferences("MedicineIndex",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Integer te=sharedPreferences.getInt("Index",1);
        te++;
        editor.putInt("Index",te);
        editor.apply();
        return (te-1);
    }

}
