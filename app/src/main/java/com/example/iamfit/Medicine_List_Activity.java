package com.example.iamfit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Medicine_List_Activity extends AppCompatActivity implements Medicine_List_Adapter.ResultListener {

    private RecyclerView recyclerView;
    ImageButton newMedicineButton,backButton;
    private ArrayList<Medicine> mediciness=new ArrayList<Medicine>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine__list_);
        recyclerView=findViewById(R.id.recyclerViewMedicine);
        newMedicineButton=findViewById(R.id.imageButton2);
        backButton=findViewById(R.id.imageButton);

        newMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Medicine_List_Activity.this, MedicineSetActivity.class);
                startActivity(i);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
               /* Intent i = new Intent(Medicine_List_Activity.this, HomeActivity.class);
                startActivity(i);*/
            }
        });



       /* SharedPreferences sharedPreferences =getSharedPreferences("MedicineList",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=gson.toJson(mediciness);
        editor.putString("ListOfTheMedicines",json);
        editor.apply();*/





        loadMedicineData();
        Toast.makeText(Medicine_List_Activity.this, "Hi " +mediciness.size(), Toast.LENGTH_LONG).show();
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        List<medicinePageModelClass> medicineList=new ArrayList<>();

        //here u should add a function to add medicine in the mediineList Array
        //every element of the array should contain 3 things.
        //first one is a int.pass 1 if user have to take the medicine with empty stomach.Otherwise,pass 0 or any other number.
        //second one is a string which contains the medicine name.
        //third one is a string again.To show the time when he or she should take the medicine.recommended: ( "at "+time+" "+pm/am)
        for(Medicine med:mediciness){
            medicinePageModelClass temp;
            String time=new String();
            String AMPM=new String();
            Integer hour,minute;
            if(med.getTaken()){
                continue;
            }
            if(med.getMinute()<10){
                time=":0"+med.getMinute();
            }
            else{
                time= ":"+med.getMinute();
            }
            if(med.getHour()>12){
                hour=(med.getHour()-12);
                AMPM=" PM";
            }
            else{
                hour=med.getHour();
                AMPM=" AM";
            }
            temp=new medicinePageModelClass(med.getCheckBoxInt(),med.getName(),"At "+hour+time+AMPM);
            medicineList.add(temp);
        }
        Medicine_List_Adapter adapter=new Medicine_List_Adapter(Medicine_List_Activity.this,medicineList,Medicine_List_Activity.this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
    @Override
    public void onResultClick (final int positon){
        Integer s = positon;
        //Toast.makeText(Medicine_List_Activity.this, "Clicked " + s.toString(), Toast.LENGTH_SHORT).show();
        AlertDialog dialog=new AlertDialog.Builder(Medicine_List_Activity.this)
                .setTitle("Medicine Taken")
                .setMessage("Have you taken this medicine?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        Intent intent = new Intent(Medicine_List_Activity.this, ReminderBroadcast.class);
                        int it=loadAndRetrieveIterator(Medicine_List_Activity.this);
                        PendingIntent pendingIntent = PendingIntent
                                .getBroadcast(Medicine_List_Activity.this, positon+it, intent, 0);
                        Calendar calender =Calendar.getInstance();
                        int hour=calender.get(Calendar.HOUR_OF_DAY);
                        int minute=calender.get(Calendar.MINUTE);
                        int cS=calender.get(Calendar.SECOND);
                        if(mediciness.get(positon).getHour()<hour){
                            hour=(mediciness.get(positon).getHour()+24)-hour;
                        }else{
                            hour=mediciness.get(positon).getHour()-hour;
                        }
                        minute=mediciness.get(positon).getMinute()-minute;
                        hour=hour*60+minute;
                        if(mediciness.get(positon).getRemainingDays()>0){
                            mediciness.get(positon).setRemainingDays(mediciness.get(positon).getRemainingDays()-1);
                            mediciness.get(positon).setDif(hour);
                            mediciness.get(positon).setTaken(true);
                            long now = System.currentTimeMillis() - cS * 1000;
                            alarmManager.set(AlarmManager.RTC_WAKEUP,now+mediciness.get(positon).getDif()*60*1000
                                    ,pendingIntent);
                        }
                        Integer temp;
                        temp = 1;
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
                        }
                        saveMedicineData();
                        /*Intent i = new Intent(Medicine_List_Activity.this, Medicine_List_Activity.class);
                        startActivity(i);*/
                        List<medicinePageModelClass> medicineList=new ArrayList<>();
                        for(Medicine med:mediciness){
                            medicinePageModelClass temps;
                            String time=new String();
                            String AMPM=new String();
                            Integer hours,minutes;
                            if(med.getTaken()){
                               continue;
                            }
                            if(med.getMinute()<10){
                                time=":0"+med.getMinute();
                            }
                            else{
                                time= ":"+med.getMinute();
                            }
                            if(med.getHour()>12){
                                hours=(med.getHour()-12);
                                AMPM=" PM";
                            }
                            else{
                                hours=med.getHour();
                                AMPM=" AM";
                            }
                            temps=new medicinePageModelClass(med.getCheckBoxInt(),med.getName(),"At "+hours+time+AMPM);
                            medicineList.add(temps);
                        }
                        Medicine_List_Adapter adapter=new Medicine_List_Adapter(Medicine_List_Activity.this,medicineList,Medicine_List_Activity.this);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                })
                .setNegativeButton("Canecl",null).create();
        dialog.show();
        //Intent intent = new Intent(this, ParentSearchResultProfileActivity.class);
       // startActivity(intent);
    }
    private int loadAndRetrieveIterator(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences("MedicineIterator",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        int te=sharedPreferences.getInt("Iterator",0);
        te++;
        editor.putInt("Iterator",te);
        editor.apply();
        return (te-1);
    }

}