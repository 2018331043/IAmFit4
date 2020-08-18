package com.example.iamfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ParentSearchActivity extends AppCompatActivity implements SearchAdapter.OnResultListener{
    public static final String EXTRA_USERID="com.example.iamfit.EXTRA_USRID";
    private EditText searcText;
    private Button searchButton;
    private RecyclerView recyclerView;
    public DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    ArrayList<String> names;
    ArrayList<String> Uids;
    SearchAdapter searchAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_search);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        searcText=findViewById(R.id.searchField);
        searchButton=findViewById(R.id.searchButton);
        recyclerView=findViewById(R.id.searchResult);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        //Toast.makeText(ParentSearchActivity.this, "entered", Toast.LENGTH_SHORT).show();

        names=new ArrayList<>();
        Uids=new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        searcText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()){
                    setAdapter(s.toString());
                }
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=searcText.getText().toString();
                //firebaseUsersSearch(text);
            }
        });
    }

    private void setAdapter(final String string) {

        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                names.clear();
                Uids.clear();
                int cnt=0;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String uid= snapshot.getKey();
                    String name=snapshot.child("name").getValue().toString();
                    if(name.toLowerCase().contains(string.toLowerCase())){
                       // Toast.makeText(ParentSearchActivity.this, "found", Toast.LENGTH_SHORT).show();
                        names.add(name);
                        Uids.add(uid);
                        cnt++;
                    }
                    if(cnt>=7)break;
                }
                searchAdapter =  new SearchAdapter(ParentSearchActivity.this,names,Uids,ParentSearchActivity.this);
                recyclerView.setAdapter(searchAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onResultClick(int positon){
        Integer s=positon;
        Toast.makeText(ParentSearchActivity.this, "Clicked "+s.toString(), Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,UserProfileActivity.class);
        intent.putExtra(EXTRA_USERID,Uids.get(s));
        startActivity(intent);
    }

}
