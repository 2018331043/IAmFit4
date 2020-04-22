package com.example.iamfit;

import java.util.ArrayList;

public class User {
    private String Name,Email,height,weight,stepcount;
    //fuad

    //fuad

    public User() {

    }

    public User(String name, String email, String height, String weight, String stepcount) {
        Name = name;
        Email = email;
        this.height = height;
        this.weight = weight;
        this.stepcount=stepcount;
    }

    public String getName() {
        return Name;
    }

    public String getStepcount() {
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
