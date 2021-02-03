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
        String date = String.valueOf(personalSettings.getGoalDate()).substring(2);
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
        } else if(personalSettings.getGender() == 'F'){
            view.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.gender_selection_female));
        }

    }

    public void goBack(View view){
        this.finish();
    }

    public void submitPersonalSettings(View view) {

    }
}


