package com.caveryschool.waistwatcher;

public class Weight {
    private int ID;
    private float Weight;
    private float CreatedOnDate;

    public Weight(float weight, float createdOnDate) {
        Weight = weight;
        CreatedOnDate = createdOnDate;
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

    public float getCreatedOnDate() {
        return CreatedOnDate;
    }

    public void setCreatedOnDate(int createdOnDate) {
        CreatedOnDate = createdOnDate;
    }
}
