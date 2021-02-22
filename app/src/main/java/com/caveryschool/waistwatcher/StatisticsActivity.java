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
    private float _previousWeight;
    private float _firstWeight;

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
        setSinceLastWeight();
        // set since start field
        setSinceFirstWeight();
    }

    private void setSinceFirstWeight() {
        // get the field
        TextView sinceFirstWeight = (TextView) findViewById(R.id.since_starting_field);

        // calculate
        float change = this._currentWeight - this._firstWeight;

        String sign = "";
        if(change > 0){
            sign = "⇗";
        } else {
            sign = "⇘";
        }

        // set the field
        sinceFirstWeight.setText(sign + String.valueOf(Math.abs(change)));
    }

    private void setSinceLastWeight() {
        // get the field
        TextView sinceLastWeight = (TextView) findViewById(R.id.since_last_weight_field);

        // calculate
        float change = this._currentWeight - this._previousWeight;

        String sign = "";
        if(change > 0){
            sign = "⇗";
        } else {
            sign = "⇘";
        }

        // set the field
        sinceLastWeight.setText(sign + String.valueOf(Math.abs(change)));
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
        // get height from the settings in the database
        PersonalSettings personalSettings = this._databaseManager.getPersonalSettings();
        int tempHeightInchValue = personalSettings.getHeightInInches();
        int feet = personalSettings.getHeightInFeet();
        int heightInches = feet * 12 + tempHeightInchValue;
        // 703 X Weight in lbs / height in inches squared
        float calculatedBMI = (703 * this._currentWeight) / (heightInches * heightInches);
        // get height


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
            Weight previousWeight = new Weight(0, 0, 0);
            for (int i=0; i < weights.size(); i++) {
                if(i == 0){
                    this._firstWeight = weights.get(i).getWeight();
                }
                if(weights.get(i).getID() > currentWeight.getID()) {
                    currentWeight = weights.get(i);
                    if(i > 0){
                        previousWeight = weights.get(i-1);
                    }
                }
            }
            this._previousWeight = previousWeight.getWeight();
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
