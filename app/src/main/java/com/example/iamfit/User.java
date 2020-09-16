package com.example.iamfit;

import java.util.ArrayList;

public class User {
    private String Name,Email,height,weight,Gender;
    private  Integer month,day,year;
    private ArrayList<StepCount> stepCounts;


    public void setName(String name) {
        Name = name;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public ArrayList<StepCount> getStepCounts() {
        return stepCounts;
    }

    public void setStepCounts(ArrayList<StepCount> stepCounts) {
        this.stepCounts = stepCounts;
    }

    public User() {

    }

    public User(String name, String email, String height, String weight, ArrayList<StepCount> stepCounts,Integer month,Integer day,Integer year,String gender) {
        Name = name;
        Email = email;
        this.height = height;
        this.weight = weight;
        this.stepCounts=stepCounts;
        this.month=month;
        this.day=day;
        this.year=year;
        this.Gender=gender;
    }

    public String getName() {
        return Name;
    }



    public String getEmail() {
        return Email;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

}
