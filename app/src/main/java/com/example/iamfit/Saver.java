package com.example.iamfit;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class Saver extends AppCompatActivity {
    public void saveintopf(String sharedpfname,String key,String value){
        SharedPreferences sharedPreferences=getSharedPreferences(sharedpfname, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }
    public String readinfrompf(String sharedpfname,String key){
        SharedPreferences sharedPreferences=getSharedPreferences(sharedpfname, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"No value");
    }
}
