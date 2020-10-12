package com.example.iamfit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import java.util.ArrayList;
import java.util.List;

public class Medicine_List_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine__list_);
        recyclerView=findViewById(R.id.recyclerViewMedicine);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        List<medicinePageModelClass> medicineList=new ArrayList<>();

        //here u should add a function to add medicine in the mediineList Array
        //every element of the array should contain 3 things.
        //first one is a int.pass 1 if user have to take the medicine with empty stomach.Otherwise,pass 0 or any other number.
        //second one is a string which contains the medicine name.
        //third one is a string again.To show the time when he or she should take the medicine.recommended: ( "at "+time+" "+pm/am)

        medicineList.add(new medicinePageModelClass(1,"Napa","At 10.00 PM"));
        medicineList.add(new medicinePageModelClass(0,"Fexo","At 9.00 PM"));

        Medicine_List_Adapter adapter=new Medicine_List_Adapter(medicineList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}