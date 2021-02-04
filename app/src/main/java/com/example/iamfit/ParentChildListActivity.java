package com.example.iamfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ParentChildListActivity extends AppCompatActivity implements RecyclerViewChildAdapter.ResultListener {

    private RecyclerView recyclerView;
    private ImageButton imageButton;
    private TextView textView;
    private DatabaseReference databaseReference;
    private List<RecyclerViewChildModelClass> childList=new ArrayList<>();

    private Dialog dialogChild,dialogStepGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_child_list);
        textView=(TextView)findViewById(R.id.textView31);
        imageButton=(ImageButton)findViewById(R.id.imageButton3);



        dialogChild=new Dialog(ParentChildListActivity.this);
        dialogChild.setContentView(R.layout.custom_dialog_1);
        dialogChild.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background_1));
        dialogChild.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogChild.getWindow().getAttributes().windowAnimations=R.style.animation1;

        dialogStepGoal=new Dialog(ParentChildListActivity.this);
        dialogStepGoal.setContentView(R.layout.custom_dialog_2);
        dialogStepGoal.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background_1));
        dialogStepGoal.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogStepGoal.getWindow().getAttributes().windowAnimations=R.style.animation1;
        /*textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogChild.show();
            }
        });*/
        /*textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogStepGoal.show();
            }
        });*/
        //dialogChild.show();
        //to display the dialog

        imageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recyclerView=(RecyclerView)findViewById(R.id.recyclerViewChild);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        //here you should add data to child view Recycler
         //recyclerViewChildModelClassList.add(new RecyclerViewChildModelClass(R.drawable.profile),"Ahmed Iftekher","Age : 21");
        // RecyclerViewChildAdapter adapter=new RecyclerViewChildAdapter(ParentChildListActivity.this,medicineList,Medicine_List_Activity.this);
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User currentUser=dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue(User.class);
                ArrayList<String> uids=currentUser.getChilds();

                if(uids.size()!=1||!uids.get(0).equals("0")){
                    //childList.clear();

                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        String user=snapshot.getKey();

                        for(int i=0;i<uids.size();i++){
                            if(user.equals(uids.get(i))){
                                //Toast.makeText(ParentChildListActivity.this, "Clicked ", Toast.LENGTH_SHORT).show();
                                User tempUser=snapshot.getValue(User.class);
                                String tempName=snapshot.child("name").getValue().toString();
                                String birthDay=snapshot.child("year").getValue().toString();
                                Calendar calendar= Calendar.getInstance();
                                Integer year=calendar.get(Calendar.YEAR);

                                int temped=1;
                                int birthyear;
                                birthyear=0;
                                for(int j=birthDay.length()-1;j>=0;j--){
                                    birthyear=birthyear+((birthDay.toCharArray()[j]-48)*temped);

                                    temped=temped*10;
                                }
                                //Toast.makeText(ParentChildListActivity.this, "Clicked "+birthyear+" "+year, Toast.LENGTH_SHORT).show();
                                Integer age=year-birthyear;
                                String imageuri;
                                if(!tempUser.getImageurl().equals("0")){
                                    imageuri=tempUser.getImageurl();
                                }else{
                                    imageuri="0";
                                }
                                RecyclerViewChildModelClass temprorary=new RecyclerViewChildModelClass(imageuri,tempName,"Age: "+age.toString());
                                childList.add(temprorary);
                            }
                        }
                    }
                    RecyclerViewChildAdapter adapter=new RecyclerViewChildAdapter(ParentChildListActivity.this,childList,ParentChildListActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    @Override
    public void onResultClick (final int positon){
        Integer s = positon;
        Toast.makeText(ParentChildListActivity.this, "Clicked "+s, Toast.LENGTH_LONG).show();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(ParentChildListActivity.this, "Clicked in"+positon, Toast.LENGTH_LONG).show();
                User currentUser=dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue(User.class);
                final String chilUid=currentUser.getChilds().get(positon);
                final User childUser= dataSnapshot.child(chilUid).getValue(User.class);
                AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(ParentChildListActivity.this);
                View dialogView= getLayoutInflater().inflate(R.layout.custom_dialog_1,null);
                Button setStepGoal= (Button)dialogView.findViewById(R.id.button7);
                Button profileView= (Button)dialogView.findViewById(R.id.button8);
                dialogBuilder.setView(dialogView);
                AlertDialog alertDialog=dialogBuilder.create();
                alertDialog.show();
                setStepGoal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ParentChildListActivity.this, "Clicked on Layout "+positon, Toast.LENGTH_LONG).show();
                        View setgoalDialogView= getLayoutInflater().inflate(R.layout.custom_dialog_2,null);
                        final EditText goal=(EditText) setgoalDialogView.findViewById(R.id.editTextNumber3);

                        Button confirmation=(Button) setgoalDialogView.findViewById(R.id.button9);
                        AlertDialog.Builder setGoalBuilder=new AlertDialog.Builder(ParentChildListActivity.this);
                        setGoalBuilder.setView(setgoalDialogView);
                        AlertDialog goalDialog = setGoalBuilder.create();
                        goalDialog.show();
                        confirmation.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v){
                                final String strGoal=goal.getText().toString();
                                Toast.makeText(ParentChildListActivity.this, "The new goal is "+strGoal, Toast.LENGTH_LONG).show();
                                int goalin=0,temp=0;
                                for(int i=strGoal.length()-1;i>=0;i--){
                                    goalin+=(strGoal.toCharArray()[i]-48)*Math.pow(10,temp);
                                    temp++;
                                }
                                childUser.setCurrentStepGoal(goalin);
                                databaseReference.child(chilUid).setValue(childUser);
                            }
                        });
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}