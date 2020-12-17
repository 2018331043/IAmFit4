package com.example.iamfit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ParentChildListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_child_list);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerViewChild);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        List<RecyclerViewChildModelClass>recyclerViewChildModelClassList=new ArrayList<>();
        //here you should add data to child view Recycler
       // recyclerViewChildModelClassList.add(new RecyclerViewChildModelClass(R.drawable.profile),"Ahmed Iftekher","Age : 21");
    }
}