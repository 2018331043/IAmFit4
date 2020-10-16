package com.example.iamfit;

public class Medicine {
   private int dif,hour,minute,remainingDays,checkBoxInt;
    private Boolean taken;
    private String Name;
    public Medicine(){

    }
    public Medicine(int dif, int hour, int minute, int remainingDays, String name,int CheckBoxInt,Boolean taken2) {
        this.dif = dif;
        this.hour = hour;
        this.minute = minute;
        this.remainingDays = remainingDays;
        this.checkBoxInt=CheckBoxInt;
        this.taken=taken2;
        Name = name;
    }

    public Boolean getTaken() {
        return taken;
    }

    public void setTaken(Boolean taken) {
        this.taken = taken;
    }

    public int getCheckBoxInt() {
        return checkBoxInt;
    }

    public void setCheckBoxInt(int checkBoxInt) {
        this.checkBoxInt = checkBoxInt;
    }

    public int getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(int remainingDays) {
        this.remainingDays = remainingDays;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getDif() {
        return dif;
    }

    public void setDif(int dif) {
        this.dif = dif;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
