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

        // subtract 1 from it
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
}
