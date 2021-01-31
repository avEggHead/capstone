package com.caveryschool.waistwatcher;

public class PersonalSettings {
    private int id;
    private float goalWeight;
    private int goalDate;
    private char gender;
    private int heightInFeet;
    private int heightInInches;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getGoalWeight() {
        return goalWeight;
    }

    public void setGoalWeight(float goalWeight) {
        this.goalWeight = goalWeight;
    }

    public int getGoalDate() {
        return goalDate;
    }

    public void setGoalDate(int goalDate) {
        this.goalDate = goalDate;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getHeightInFeet() {
        return heightInFeet;
    }

    public void setHeightInFeet(int heightInFeet) {
        this.heightInFeet = heightInFeet;
    }

    public int getHeightInInches() {
        return heightInInches;
    }

    public void setHeightInInches(int heightInInches) {
        this.heightInInches = heightInInches;
    }
}
