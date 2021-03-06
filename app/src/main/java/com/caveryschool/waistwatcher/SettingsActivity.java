package com.caveryschool.waistwatcher;

import android.app.Person;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SettingsActivity extends AppCompatActivity {
    private DatabaseManager _databaseManager;
    private ConstraintLayout _layout;
    private boolean _clearAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this._layout = findViewById(R.id.settings_screen);
        this._databaseManager = new DatabaseManager(this);
        setPersonalSettings();
    }

    private void setPersonalSettings() {
        PersonalSettings personalSettings = this._databaseManager.getPersonalSettings();
        setUIFields(personalSettings);
    }

    private void setUIFields(PersonalSettings personalSettings) {
        setGoalWeight(personalSettings);
        setGoalDate(personalSettings);
        setGenderIcon(personalSettings);
        setHeight(personalSettings);
    }

    private void setHeight(PersonalSettings personalSettings) {
        setHeightFeet(personalSettings);
        setHeightInches(personalSettings);
    }

    private void setHeightInches(PersonalSettings personalSettings) {
        EditText heightInInchesField = this._layout.findViewById(R.id.height_in_inches);
        String heightInInchesValue = String.valueOf(personalSettings.getHeightInInches());
        heightInInchesField.setText(heightInInchesValue);
    }

    private void setHeightFeet(PersonalSettings personalSettings) {
        EditText heightInFeetField = this._layout.findViewById(R.id.height_in_feet);
        String heightInFeetValue = String.valueOf(personalSettings.getHeightInFeet());
        heightInFeetField.setText(heightInFeetValue);
    }

    private void setGoalDate(PersonalSettings personalSettings) {
        // if the settings screen was started by the date picker than use the data from the date picker.
        TextView goalDate = this._layout.findViewById(R.id.input_goal_date);
        String date = "";
        Bundle bundle = getIntent().getExtras();
        if(bundle != null && !this._clearAll){
            date = bundle.getString("date");
        }else{
            date = String.valueOf(personalSettings.getGoalDate());
        }
        goalDate.setText(date);
    }

    private void setGoalWeight(PersonalSettings personalSettings) {
        EditText goalWeight = this._layout.findViewById(R.id.input_goal_weight);

        String weight = "";
        Bundle bundle = getIntent().getExtras();
        if(bundle != null && !this._clearAll){
            weight = String.valueOf(bundle.getFloat("weight"));
        }else{
            weight = String.valueOf(personalSettings.getGoalWeight());
        }
        goalWeight.setText(weight);
    }

    private void setGenderIcon(PersonalSettings personalSettings) {
        ImageView view = this._layout.findViewById(R.id.gender_field);

        char gender = '0';
        Bundle bundle = getIntent().getExtras();
        if(bundle != null && !this._clearAll){
            gender = bundle.getChar("gender");
        }else{
            gender = personalSettings.getGender();
        }

        if(gender == 'M'){
            view.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.gender_selection_male));
            view.setTag("M");
        } else if(gender == 'F'){
            view.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.gender_selection_female));
            view.setTag("F");
        }

    }

    public void goBack(View view){
        Intent mainScreen = new Intent(this,MainActivity.class);
        this.startActivity(mainScreen);
    }

    public void selectMale(View view) {
        ImageView genderFieldImage = this._layout.findViewById(R.id.gender_field);
        genderFieldImage.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.gender_selection_male));
        genderFieldImage.setTag("M");

    }

    public void selectFemale(View view) {
        ImageView genderFieldImage = this._layout.findViewById(R.id.gender_field);
        genderFieldImage.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.gender_selection_female));
        genderFieldImage.setTag("F");
    }

    public void clearAll(View view){
        this._clearAll = true;
        setPersonalSettings();
    }

    public void submitPersonalSettings(View view) {
        // get all the values and set up the personal settings object for insert
        EditText goalWeightField = this._layout.findViewById(R.id.input_goal_weight);
        TextView goalDateField = this._layout.findViewById(R.id.input_goal_date);
        ImageView genderFieldImage = this._layout.findViewById(R.id.gender_field);
        EditText heightInFeetField = this._layout.findViewById(R.id.height_in_feet);
        EditText heightInInchesField = this._layout.findViewById(R.id.height_in_inches);

        PersonalSettings personalSettings = new PersonalSettings();

        if(goalWeightField.getText().length() > 0){
            float goalWeight = Float.parseFloat(String.valueOf(goalWeightField.getText()));
            personalSettings.setGoalWeight(goalWeight);
        }

        if(goalDateField.getText().length() > 0){
            int goalDate = Integer.parseInt(String.valueOf(goalDateField.getText()));
            personalSettings.setGoalDate(goalDate);
        }

        if(heightInFeetField.getText().length() > 0){
            int feet = Integer.parseInt(String.valueOf(heightInFeetField.getText()));
            personalSettings.setHeightInFeet(feet);
        }

        if(heightInInchesField.getText().length() > 0){
            int inches = Integer.parseInt(String.valueOf(heightInInchesField.getText()));
            personalSettings.setHeightInInches(inches);
        }

        char gender = String.valueOf(genderFieldImage.getTag()).charAt(0);
        personalSettings.setGender(gender);

        // insert the object into the database
        this._databaseManager.upsertPersonalSettings(personalSettings);

        this.goBack(null);
    }

    public void goToDatePicker(View view) {
        Intent goalDatePicker = new Intent(this,GoalDatePickerActivity.class);

        Bundle currentSettingsBundle = new Bundle();
        EditText goalWeightField = this._layout.findViewById(R.id.input_goal_weight);
        TextView goalDateField = this._layout.findViewById(R.id.input_goal_date);
        ImageView genderFieldImage = this._layout.findViewById(R.id.gender_field);
        EditText heightInFeetField = this._layout.findViewById(R.id.height_in_feet);
        EditText heightInInchesField = this._layout.findViewById(R.id.height_in_inches);

        currentSettingsBundle.putFloat("weight", Float.parseFloat(String.valueOf(goalWeightField.getText())));
        currentSettingsBundle.putChar("gender", String.valueOf(genderFieldImage.getTag()).charAt(0));
        // pass the data and start the new activity
        goalDatePicker.putExtras(currentSettingsBundle);

        this.startActivity(goalDatePicker);
    }
}


