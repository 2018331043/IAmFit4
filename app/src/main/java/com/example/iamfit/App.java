package com.example.iamfit;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
/**
 * A simple class to create the Notification channel for the medicine intake
 */
public class App extends Application {
    public static final String MEDICINE_TAKE="Medicine_take";
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }
    private void createNotificationChannels(){
        /*checking the buuld version to see if the build is higher than oreo to create necessary channel*/
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel(
                    MEDICINE_TAKE,
                    "MedicineTake",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationChannel.setDescription("This channel notifies you about taking your drugs timely");
           // notificationChannel.setLightColor();
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }
}
