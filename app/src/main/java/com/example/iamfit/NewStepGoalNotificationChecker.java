package com.example.iamfit;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users");
    public NewStepGoalNotificationChecker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User currentUser=dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue(User.class);
                currentUser.setCurrentStepGoal(3000);
                databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(currentUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Toast.makeText(getApplicationContext(), "WorkManager Triggered ", Toast.LENGTH_LONG).show();
        return null;
    }
}
