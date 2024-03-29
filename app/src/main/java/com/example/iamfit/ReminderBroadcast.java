package com.example.iamfit;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
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
        //int it=loadAndRetrieveIterator(context);


        String nameTemp=intent.getStringExtra("medicine name");
        String value=intent.getStringExtra("Iterator");
        int length = value.length();
        int index = 0;
        int temp = 0;
        for (int i = length - 1; i >= 0; i--) {
            index += (value.toCharArray()[i] - 48) * Math.pow(10, temp);
            temp++;
        }
       // =sharedPreferences.getInt("Iterator",0);
       //int it=0;
        loadMedicineData(context);
        String MedicineName=mediciness.get(index-1).getName();
        mediciness.get(index-1).setTaken(false);
        saveMedicineData(context);
        //it++;
       // editor.putInt("Iterator",it);
       // editor.commit();
       // Toast.makeText(context,"Hello : "+index+"Size : "+mediciness.size(), Toast.LENGTH_LONG).show();
        Log.v("Debuging.........", "index=" + index);


        Intent resultIntent=new Intent(context,Medicine_List_Activity.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(context,1,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        //String MedicineName="Rter";

        Notification notification=new NotificationCompat.Builder(context,App.MEDICINE_TAKE)
                .setSmallIcon(R.drawable.heart)
                .setContentTitle(MedicineName)
                .setContentText("It is time for you to take a pill of your medicine "+MedicineName)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notificationManagerCompat.notify(index+1,notification);
        //notificationManagerCompat.notify(100,notification);
       // medicineNumber++;
    }
    private void saveMedicineData(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences("MedicineList",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=gson.toJson(mediciness);
        editor.putString("ListOfTheMedicines",json);
        editor.apply();
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
   /* private int loadAndRetrieveIterator(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences("MedicineIterator",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        int te=sharedPreferences.getInt("Iterator",0);
        te++;
        editor.putInt("Iterator",te);
        editor.apply();
        return (te-1);
    }*/
}
