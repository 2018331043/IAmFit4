package com.example.iamfit;

import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.logging.Handler;

public class NewStepGoalNotificationChecker extends Worker {
    private int prevStepGoal;
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users");
    private NotificationManagerCompat notificationManagerCompat;

    public NewStepGoalNotificationChecker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        SharedPreferences sharedPreferences3=getApplicationContext().getSharedPreferences("StepGoalSetter", Context.MODE_PRIVATE);
        prevStepGoal=sharedPreferences3.getInt("StepGoal",8000);

        notificationManagerCompat=NotificationManagerCompat.from(getApplicationContext());

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User currentUser=dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue(User.class);
                int curStepGoal=currentUser.getCurrentStepGoal();
                if(prevStepGoal!=curStepGoal){
                    SharedPreferences sharedPreferences3=getApplicationContext().getSharedPreferences("StepGoalSetter", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor2=sharedPreferences3.edit();
                    editor2.putInt("StepGoal",curStepGoal);
                    editor2.commit();
                    Notification notification=new NotificationCompat.Builder(getApplicationContext(),App.MEDICINE_TAKE)
                            .setSmallIcon(R.drawable.heart)
                            .setContentTitle("New Step Goal")
                            .setContentText("Your new Step Goal has been set to "+curStepGoal+" by your parent")
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                            .build();
                    notificationManagerCompat.notify(1000,notification);
                }
                //databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(currentUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Toast.makeText(getApplicationContext(), "WorkManager Triggered ", Toast.LENGTH_LONG).show();
        return null;
    }
}
