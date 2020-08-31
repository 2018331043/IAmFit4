package com.example.iamfit;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ReminderBroadcast extends BroadcastReceiver {
    private NotificationManagerCompat notificationManagerCompat;
    //public static ArrayList<String> MedicineNames=new ArrayList<>();
    private ArrayList<Medicine> mediciness=new ArrayList<Medicine>();
   // public static ArrayList<Medicine> Medicines=new ArrayList<Medicine>();
   // public static Integer medicineNumber=0,alarmManagerNumbers=0;
    @Override
    public void onReceive(Context context, Intent intent) {

        notificationManagerCompat=NotificationManagerCompat.from(context);
        //final String MedicineName=intent.getStringExtra(MedicineSetActivity.MedicineNames.get(MedicineSetActivity.medicineNumber));
       // String MedicineName=Medicines.get(medicineNumber).getName();
        int it=loadAndRetrieveIterator(context);

       // =sharedPreferences.getInt("Iterator",0);
       //int it=0;
        loadMedicineData(context);
        String MedicineName=mediciness.get(it).getName();
        //it++;
       // editor.putInt("Iterator",it);
       // editor.commit();
        Toast.makeText(context,"Hello : "+it, Toast.LENGTH_SHORT).show();
        Notification notification=new NotificationCompat.Builder(context,App.MEDICINE_TAKE)
                .setSmallIcon(R.drawable.heart)
                .setContentTitle(MedicineName)
                .setContentText("It is time for you to take a pill of your medicine "+MedicineName)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManagerCompat.notify(it+1,notification);
       // medicineNumber++;
    }
    public void loadMedicineData(Context context){
        mediciness.clear();
        SharedPreferences sharedPreferences =context.getSharedPreferences("MedicineList",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("ListOfTheMedicines",null);
        Type type=new TypeToken<ArrayList<Medicine>>() {}.getType();
        mediciness=gson.fromJson(json,type);
        if(mediciness==null){
            mediciness=new ArrayList<>();
        }
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
