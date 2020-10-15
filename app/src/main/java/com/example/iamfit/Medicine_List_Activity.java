package com.example.iamfit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Medicine_List_Activity extends AppCompatActivity {

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
        loadMedicineData();
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
            temp=new medicinePageModelClass(med.getCheckBoxInt(),med.getName(),"At "+med.getHour()+"."+med.getMinute());
            medicineList.add(temp);
        }

        //medicineList.add(new medicinePageModelClass(1,"Napa","At 10.00 PM"));
        //medicineList.add(new medicinePageModelClass(0,"Fexo","At 9.00 PM"));

        Medicine_List_Adapter adapter=new Medicine_List_Adapter(medicineList);
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
}