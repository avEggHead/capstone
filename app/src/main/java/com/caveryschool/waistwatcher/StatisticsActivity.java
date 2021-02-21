package com.caveryschool.waistwatcher;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.List;

public class StatisticsActivity extends AppCompatActivity {
    private DatabaseManager _databaseManager;
    private float _currentWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        this._databaseManager = new DatabaseManager(this);
    }

    public void onStart(){
        super.onStart();
        setStatisticsFields();
    }

    private void setStatisticsFields() {
        // set current weight field
        setCurrentWeightField();
        // set BMI field
        setBMIField();
        // set since last field

        // set since start field

    }

    private void setBMIField() {
        // get the field
        TextView BMIField = (TextView) findViewById(R.id.bmi_field);
        // use current weight and height to calculate
        String bmiValue = "23";
        bmiValue = calculateBMI();
        // set the field to the calculated
        BMIField.setText(bmiValue);
    }

    private String calculateBMI() {
        float calculatedBMI = 25;
        return String.valueOf(calculatedBMI);
    }

    private void setCurrentWeightField() {
        // get the field
        TextView currentWeightField = (TextView) findViewById(R.id.current_weight_field);
        // get the value
        List<Weight> weights = this._databaseManager.selectAll();
        String outputValue = "";
        if(!weights.isEmpty()){
            Weight currentWeight = new Weight(0, 0, 0);
            for (int i=0; i < weights.size(); i++) {
                if(weights.get(i).getID() > currentWeight.getID()) {
                    currentWeight = weights.get(i);
                }
            }
            this._currentWeight = currentWeight.getWeight();
            outputValue = String.valueOf(this._currentWeight);
        }
        // put the value in the field
        currentWeightField.setText(outputValue);
    }

    public void goBack(View view) {
        this.finish();
    }
}
