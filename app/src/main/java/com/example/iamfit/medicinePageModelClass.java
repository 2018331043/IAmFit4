package com.example.iamfit;

import android.widget.CheckBox;

public class medicinePageModelClass {
    private int checkBoxInt;
    private String medicineName,time;

    public medicinePageModelClass(int checkBoxInt, String medicineName, String time) {
        this.checkBoxInt = checkBoxInt;
        this.medicineName = medicineName;
        this.time = time;
    }

    public int getCheckBoxInt() {
        return checkBoxInt;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getTime() {
        return time;
    }
}
