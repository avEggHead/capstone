package com.caveryschool.waistwatcher;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class StatisticsActivity extends AppCompatActivity {
    private DatabaseManager _databaseManager;
    private float _currentWeight;
    private float _previousWeight;
    private float _firstWeight;
    private PersonalSettings _personalSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        this._databaseManager = new DatabaseManager(this);
    }

    public void onStart(){
        super.onStart();
        this._personalSettings = this._databaseManager.getPersonalSettings();
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
        // set the days left field
        setDaysLeft();
        // set the above or below image and field
        setDeltaGoal();
    }

    private void setDeltaGoal() {
        // get the current weight
        float currentWeight = this._currentWeight;

        // get the goal weight
        float goalWeight = this._personalSettings.getGoalWeight();

        // find the difference
        float difference = Math.abs(goalWeight - currentWeight);

        // select the image based on a positive or negative difference
        ImageView aboveOrBelow = this.findViewById(R.id.above_or_below_header);

        if (currentWeight > goalWeight){
            // set the image to above
            // get the image view
            aboveOrBelow.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.above_goal_header));
        } else{
            // set the image to below
            aboveOrBelow.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.below_goal_header));
        }

        // set the int value in the field
        TextView deltaField = this.findViewById(R.id.goal_delta_field);
        deltaField.setText(String.valueOf(difference));
    }

    private void setDaysLeft() {

        TextView daysLeftField = findViewById(R.id.days_left_field);

        // get the current date
        Calendar calendar = Calendar.getInstance(Locale.US);
        Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
        Date currentDate = new Date(timestamp.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStamp = String.valueOf(timestamp.toString()).substring(0,10);

        // get the goal date
        int goalDateInt = this._personalSettings.getGoalDate();
        String goalDateString = String.valueOf(goalDateInt);
        if(goalDateInt > 0){
            int yearInt = Integer.parseInt(goalDateString.substring(0,4));
            int monthInt = Integer.parseInt(goalDateString.substring(4,6));
            int dayInt = Integer.parseInt(goalDateString.substring(6,8));
            calendar.set(yearInt, monthInt - 1, dayInt);
            Timestamp timestamp2 = new Timestamp(calendar.getTimeInMillis());
            Date goalDate = new Date(timestamp2.getTime());
            // find the difference in days between goal date and current date
            long deltaDays = TimeUnit.DAYS.convert(goalDate.getTime() - currentDate.getTime(), TimeUnit.MILLISECONDS);
            // set the field with the delta
            daysLeftField.setText(String.valueOf(deltaDays));
        }
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
        int tempHeightInchValue = this._personalSettings.getHeightInInches();
        int feet = this._personalSettings.getHeightInFeet();
        int heightInches = feet * 12 + tempHeightInchValue;
        // 703 X Weight in lbs / height in inches squared
        if(heightInches != 0){
            float calculatedBMI = (703 * this._currentWeight) / (heightInches * heightInches);
            // get height
            return String.valueOf(calculatedBMI);
        }
        return "0";
    }

    private void setCurrentWeightField() {
        // get the field
        TextView currentWeightField = (TextView) findViewById(R.id.current_weight_field);
        // get the value
        List<Weight> weights = this._databaseManager.selectAll();
        String outputValue = "";
        if(!weights.isEmpty()){
            Weight currentWeight = new Weight(0, 0, 0, "");
            Weight previousWeight = new Weight(0, 0, 0, "");
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
