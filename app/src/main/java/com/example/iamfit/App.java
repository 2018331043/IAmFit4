package com.example.iamfit;
/*
Notiifcation channel for medicine reminder notifications
*/
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String MEDICINE_TAKE="Medicine_take";
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }
    private void createNotificationChannels(){
        //it creates a notification channel base on the build version of the app on the device if it's greater than oreo
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
