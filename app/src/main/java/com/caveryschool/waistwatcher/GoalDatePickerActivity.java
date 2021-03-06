package com.caveryschool.waistwatcher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class GoalDatePickerActivity extends AppCompatActivity {
    ConstraintLayout _layout;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goaldatepicker);
        this._layout = findViewById(R.id.goal_date_picker);
    }

    public void decrementYear(View view) {
        // get the current value of the text field
        TextView yearField = this._layout.findViewById(R.id.year_value);
        int yearInt = Integer.parseInt(yearField.getText().toString());

        // subtract 1 from it
        int newYearInt = yearInt - 1;

        // if month is feb check to see if the day needs adjusted and adjust it
        this.checkIfFebAndUpdateDay(newYearInt);

        // set the new value in the field
        yearField.setText(String.valueOf(newYearInt));
    }

    private void checkIfFebAndUpdateDay(int newYearInt) {
        TextView monthField = this.findViewById(R.id.month_value);
        int month = Integer.parseInt(monthField.getText().toString());
        if(month == 2){
            // if it is feb than we need to check the day
            this.setDayIfItIsOutOfRangeInNewMonth(month, newYearInt);
        }
    }

    public void incrementYear(View view) {
        // get the current value of the text field
        TextView yearField = this._layout.findViewById(R.id.year_value);
        int yearInt = Integer.parseInt(yearField.getText().toString());

        // add 1 to it
        int newYearInt = yearInt + 1;

        // if month is feb check to see if the day needs adjusted and adjust it
        this.checkIfFebAndUpdateDay(newYearInt);

        // set the new value in the field
        yearField.setText(String.valueOf(newYearInt));
    }

    public void submitDate(View view) {
        // get the settings activity
        Intent settingsActivity = new Intent(this, SettingsActivity.class);

        // get the date data
        TextView yearField = this._layout.findViewById(R.id.year_value);
        int yearInt = Integer.parseInt(yearField.getText().toString());
        TextView monthField = this._layout.findViewById(R.id.month_value);
        int monthInt = Integer.parseInt(monthField.getText().toString());
        TextView dayField = this._layout.findViewById(R.id.day_value);
        int dayInt = Integer.parseInt(dayField.getText().toString());
        // bundle up the data
        String monthString = (""+(100+monthInt)).substring(1);
        String dayString = (""+(100+dayInt)).substring(1);
        String dateForBundle = String.valueOf(yearInt) + monthString + dayString;


        Bundle dataFromSettings = getIntent().getExtras();
 
        float weight = dataFromSettings.getFloat("weight");
        char gender = dataFromSettings.getChar("gender");
        int heightInFeet = dataFromSettings.getInt("heightInFeet");
        int heightInInches = dataFromSettings.getInt("heightInInches");

        Bundle returnBundle = new Bundle();
        returnBundle.putString("date", dateForBundle);
        returnBundle.putFloat("weight", weight);
        returnBundle.putChar("gender", gender);
        returnBundle.putInt("heightInFeet", heightInFeet);
        returnBundle.putInt("heightInInches", heightInInches);

        // pass the data and start the new activity
        settingsActivity.putExtras(returnBundle);
        this.startActivity(settingsActivity);
    }

    public void goBack(View view){
        Intent settingsScreen = new Intent(this,SettingsActivity.class);

        Bundle dataFromSettings = getIntent().getExtras();

        float weight = dataFromSettings.getFloat("weight");
        char gender = dataFromSettings.getChar("gender");
        int dateForBundle = dataFromSettings.getInt("date");
        int heightInFeet = dataFromSettings.getInt("heightInFeet");
        int heightInInches = dataFromSettings.getInt("heightInInches");

        Bundle returnBundle = new Bundle();
        returnBundle.putString("date", String.valueOf(dateForBundle));
        returnBundle.putFloat("weight", weight);
        returnBundle.putChar("gender", gender);
        returnBundle.putInt("heightInFeet", heightInFeet);
        returnBundle.putInt("heightInInches", heightInInches);

        // pass the data and start the new activity
        settingsScreen.putExtras(returnBundle);
        this.startActivity(settingsScreen);
    }

    public void incrementMonth(View view) {
        // get the current value of the text field
        TextView monthField = this._layout.findViewById(R.id.month_value);
        int monthInt = Integer.parseInt(monthField.getText().toString());

        // add 1 to it if the total is less than 12 otherwise circle around
        int newMonthInt = 0;
        if(monthInt < 12){
            newMonthInt = monthInt + 1;
        } else {
            newMonthInt = 1;
        }

        // check to make sure the day isn't out of range for the new month
        this.setDayIfItIsOutOfRangeInNewMonth(newMonthInt,0);

        // set the new value in the field
        monthField.setText(String.valueOf(newMonthInt));
    }

    private void setDayIfItIsOutOfRangeInNewMonth(int newMonthInt, int newYearInt) {
        TextView dayField = this._layout.findViewById(R.id.day_value);
        int dayInt = Integer.parseInt(dayField.getText().toString());
        int lastDayOfChosenMonthIn = getLastDayOfChosenMonth(newMonthInt, newYearInt);
        if(dayInt > lastDayOfChosenMonthIn){
            dayField.setText(String.valueOf(lastDayOfChosenMonthIn));
        }
    }

    public void decrementMonth(View view) {
        // get the current value of the text field
        TextView monthField = this._layout.findViewById(R.id.month_value);
        int monthInt = Integer.parseInt(monthField.getText().toString());

        // subtract 1 unless it is 1, if it is 1 circle around
        int newMonthInt = 0;
        if(monthInt > 1){
            newMonthInt = monthInt - 1;
        } else {
            newMonthInt = 12;
        }

        // check to make sure the day isn't out of range for the new month
        this.setDayIfItIsOutOfRangeInNewMonth(newMonthInt,0);

        // set the new value in the field
        monthField.setText(String.valueOf(newMonthInt));
    }

    public void incrementDay(View view) {
        // get the current value of the text field
        TextView dayField = this._layout.findViewById(R.id.day_value);
        int dayInt = Integer.parseInt(dayField.getText().toString());

        // add 1 to it if the total is less than 12 otherwise circle around
        int newMonthInt = 0;
        if(dayInt < this.totalDaysInChosenMonth()){
            newMonthInt = dayInt + 1;
        } else {
            newMonthInt = 1;
        }

        // set the new value in the field
        dayField.setText(String.valueOf(newMonthInt));
    }

    private int totalDaysInChosenMonth() {
        return getLastDayOfChosenMonth(0,0);
    }

    public void decrementDay(View view) {
        // get the current value of the text field
        TextView dayField = this._layout.findViewById(R.id.day_value);
        int dayInt = Integer.parseInt(dayField.getText().toString());

        // add 1 to it if the total is less than 12 otherwise circle around
        int newMonthInt = 0;
        if(dayInt > 1){
            newMonthInt = dayInt - 1;
        } else {
            newMonthInt = this.getLastDayOfChosenMonth(0,0);
        }

        // set the new value in the field
        dayField.setText(String.valueOf(newMonthInt));
    }

    private int getLastDayOfChosenMonth(int month, int year) {
        int lastDayOfMonth = 0;

        // get the month
        if (month == 0){
            TextView monthField = this.findViewById(R.id.month_value);
            month = Integer.parseInt(monthField.getText().toString());
        }

        if (year == 0){
            TextView yearField = this.findViewById(R.id.year_value);
            year = Integer.parseInt(yearField.getText().toString());
        }


        // switch based on month
        switch(month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                lastDayOfMonth = 31;
                break;
            case 2:
                lastDayOfMonth = ((year % 400) == 0) || ((year % 4 == 0) && (year % 100 != 0)) ? 29  :  28;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                lastDayOfMonth = 30;
                break;
            default:
                //
        }
        return lastDayOfMonth;
    }
}
