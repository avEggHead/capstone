package com.caveryschool.waistwatcher;

public class Weight {
    private int ID;
    private float Weight;
    private int CreatedOnDate;

    public Weight(int ID, float weight, int createdOnDate) {
        this.ID = ID;
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

    public int getCreatedOnDate() {
        return CreatedOnDate;
    }

    public void setCreatedOnDate(int createdOnDate) {
        CreatedOnDate = createdOnDate;
    }
}
