package com.example.iamfit;

public class StepCount {
    private String Date;
    private Integer Steps;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Integer getSteps() {
        return Steps;
    }

    public void setSteps(Integer steps) {
        Steps = steps;
    }

    public StepCount() {
    }

    public StepCount(String date, Integer steps) {
        Date = date;
        Steps = steps;
    }
}
