package com.caveryschool.waistwatcher;

public class Weight {
    private int ID;
    private float Weight;
    private int CreatedOnDate;
    private int CreatedOnTime;

    public int getCreatedOnTime() {
        return CreatedOnTime;
    }

    public void setCreatedOnTime(int createdOnTime) {
        CreatedOnTime = createdOnTime;
    }

    public Weight(float weight, int createdOnDate, int createdOnTime) {
        Weight = weight;
        CreatedOnDate = createdOnDate;
        CreatedOnTime = createdOnTime;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public float getWeight() {
        return Weight;
    }

    public void setWeight(float weight) {
        Weight = weight;
    }

    public int getCreatedOnDate() {
        return CreatedOnDate;
    }

    public void setCreatedOnDate(int createdOnDate) {
        CreatedOnDate = createdOnDate;
    }
}
