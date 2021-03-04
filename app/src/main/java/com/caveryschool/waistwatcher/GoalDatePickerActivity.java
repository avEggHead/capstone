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

        // set the new value in the field
        yearField.setText(String.valueOf(newYearInt));
    }

    public void incrementYear(View view) {
        // get the current value of the text field
        TextView yearField = this._layout.findViewById(R.id.year_value);
        int yearInt = Integer.parseInt(yearField.getText().toString());

        // add 1 to it
        int newYearInt = yearInt + 1;

        // set the new value in the field
        yearField.setText(String.valueOf(newYearInt));
    }

    public void submitDate(View view) {
        // get the settings activity
        Intent settingsActivity = new Intent(this, SettingsActivity.class);

        // get the date data
        TextView yearField = this._layout.findViewById(R.id.year_value);
        int yearInt = Integer.parseInt(yearField.getText().toString());

        // bundle up the data
        Bundle dateBundle = new Bundle();
        dateBundle.putString("date", String.valueOf(yearInt));

        // pass the data and start the new activity
        settingsActivity.putExtras(dateBundle);
        this.startActivity(settingsActivity);
    }

    public void goBack(View view){
        Intent settingsScreen = new Intent(this,SettingsActivity.class);
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

        // set the new value in the field
        monthField.setText(String.valueOf(newMonthInt));
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
        return getLastDayOfChosenMonth();
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
            newMonthInt = this.getLastDayOfChosenMonth();
        }

        // set the new value in the field
        dayField.setText(String.valueOf(newMonthInt));
    }

    private int getLastDayOfChosenMonth() {
        int lastDayOfMonth = 0;

        // get the month
        TextView monthField = this.findViewById(R.id.month_value);
        int month = Integer.parseInt(monthField.getText().toString());

        TextView yearField = this.findViewById(R.id.year_value);
        int year = Integer.parseInt(yearField.getText().toString());

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
