package com.example.iamfit;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {
    private NotificationManagerCompat notificationManagerCompat;
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "found", Toast.LENGTH_SHORT).show();
        notificationManagerCompat=NotificationManagerCompat.from(context);
        Notification notification=new NotificationCompat.Builder(context,App.MEDICINE_TAKE)
                .setSmallIcon(R.drawable.ic_do_not_disturb_alt_black_24dp)
                .setContentTitle("Sample Notification")
                .setContentText("This is a sample notification")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManagerCompat.notify(1,notification);
    }
}
