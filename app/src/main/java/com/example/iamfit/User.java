package com.example.iamfit;

import java.util.ArrayList;

public class User {
    private String Name,Email,height,weight;
    private int stepcount;


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

    public void setStepcount(int stepcount) {
        this.stepcount = stepcount;
    }

    //fuad

    public User() {

    }

    public User(String name, String email, String height, String weight, int stepcount) {
        Name = name;
        Email = email;
        this.height = height;
        this.weight = weight;
        this.stepcount=stepcount;
    }

    public String getName() {
        return Name;
    }

    public int getStepcount() {
        return stepcount;
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
