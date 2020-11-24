package com.example.iamfit;

public class StepCount {
    private String Date;
    private Integer Steps,goal=8000;

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

    public Integer getGoal() {
        return goal;
    }

    public void setGoal(Integer goal) {
        this.goal = goal;
    }

    public StepCount(String date, Integer steps,Integer goal) {
        Date = date;
        Steps = steps;
        this.goal=goal;
    }
}
