package com.caveryschool.waistwatcher;

import android.app.Person;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

public class SettingsActivity extends AppCompatActivity {
    private DatabaseManager _databaseManager;
    private RelativeLayout _layout;


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
        EditText goalDate = this._layout.findViewById(R.id.input_goal_date);
        String date = String.valueOf(personalSettings.getGoalDate());
        goalDate.setText(date);
    }

    private void setGoalWeight(PersonalSettings personalSettings) {
        EditText goalWeight = this._layout.findViewById(R.id.input_goal_weight);
        goalWeight.setText(String.valueOf(personalSettings.getGoalWeight()));
    }

    private void setGenderIcon(PersonalSettings personalSettings) {
        ImageView view = this._layout.findViewById(R.id.gender_field);

        if(personalSettings.getGender() == 'M'){
            view.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.gender_selection_male));
            view.setTag("M");
        } else if(personalSettings.getGender() == 'F'){
            view.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.gender_selection_female));
            view.setTag("F");
        }

    }

    public void goBack(View view){
        this.finish();
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
        setPersonalSettings();
    }

    public void submitPersonalSettings(View view) {
        // get all the values
        EditText goalWeightField = this._layout.findViewById(R.id.input_goal_weight);
        float goalWeight = Float.parseFloat(String.valueOf(goalWeightField.getText()));
        EditText goalDateField = this._layout.findViewById(R.id.input_goal_date);
        int goalDate = Integer.parseInt(String.valueOf(goalDateField.getText()));
        ImageView genderFieldImage = this._layout.findViewById(R.id.gender_field);
        char gender = String.valueOf(genderFieldImage.getTag()).charAt(0);
        EditText heightInFeetField = this._layout.findViewById(R.id.height_in_feet);
        int feet = Integer.parseInt(String.valueOf(heightInFeetField.getText()));
        EditText heightInInchesField = this._layout.findViewById(R.id.height_in_inches);
        int inches = Integer.parseInt(String.valueOf(heightInInchesField.getText()));


        // create the personal settings object
        PersonalSettings personalSettings = new PersonalSettings();
        personalSettings.setGoalWeight(goalWeight);
        personalSettings.setGoalDate(goalDate);
        personalSettings.setGender(gender);
        personalSettings.setHeightInFeet(feet);
        personalSettings.setHeightInInches(inches);

        // insert the object into the database
        this._databaseManager.upsertPersonalSettings(personalSettings);

        this.finish();
    }
}


