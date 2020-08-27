package com.example.iamfit;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;

public class ReminderBroadcast extends BroadcastReceiver {
    private NotificationManagerCompat notificationManagerCompat;
    public static ArrayList<String> MedicineNames=new ArrayList<>();
    public static Integer medicineNumber=0,alarmManagerNumbers=0;
    @Override
    public void onReceive(Context context, Intent intent) {

        notificationManagerCompat=NotificationManagerCompat.from(context);
        //final String MedicineName=intent.getStringExtra(MedicineSetActivity.MedicineNames.get(MedicineSetActivity.medicineNumber));
        String MedicineName=MedicineNames.get(medicineNumber);
        Toast.makeText(context,"Hello : ", Toast.LENGTH_SHORT).show();
        Notification notification=new NotificationCompat.Builder(context,App.MEDICINE_TAKE)
                .setSmallIcon(R.drawable.heart)
                .setContentTitle(MedicineName)
                .setContentText("It is time for you to take a pill of your medicine "+MedicineName)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManagerCompat.notify(medicineNumber+1,notification);
        medicineNumber++;
    }
}
