package com.example.iamfit;

public class RecyclerViewChildModelClass {
    private String imageResource;
    private String name;
    private String age;

    public RecyclerViewChildModelClass(String imageResource, String name, String age) {
        this.imageResource = imageResource;
        this.name = name;
        this.age = age;
    }

    public String getImageResource() {
        return imageResource;
    }

    public String getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
