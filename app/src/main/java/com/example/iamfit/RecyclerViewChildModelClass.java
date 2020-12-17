package com.example.iamfit;

public class RecyclerViewChildModelClass {
    private int imageResource;
    String name;
    String age;

    public RecyclerViewChildModelClass(int imageResource, String name, String age) {
        this.imageResource = imageResource;
        this.name = name;
        this.age = age;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}
