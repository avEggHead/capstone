package com.caveryschool.waistwatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseManager _databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this._databaseManager = new DatabaseManager(this);
    }

    public void onStart(){
        super.onStart();
        setField();
    }

    private void setField() {
        // Get the current weight field
        TextView currentWeightField = (TextView) findViewById(R.id.weight);

        // get the weight from the db
        ArrayList<Weight> weights = this._databaseManager.selectAll();
        String outputValue = "";
        if(!weights.isEmpty()){
            weights.sort(Comparator.comparing(Weight::getCreatedOnDate));
            Weight currentWeight = weights.get(0);
            outputValue = String.valueOf(currentWeight.getWeight());
        }

        // set the field with the weight
        currentWeightField.setText(outputValue);
    }

    public void goToEnterWeight(View view){
        Intent enterWeightScreen = new Intent(this,EnterWeightActivity.class);
        this.startActivity(enterWeightScreen);
    }

    public void goToViewHistory(View view) {
    }

    public void goToSettings(View view) {
    }
}