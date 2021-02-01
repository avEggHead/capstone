package com.caveryschool.waistwatcher;

import android.app.Person;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private DatabaseManager _databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this._databaseManager = new DatabaseManager(this);
        // hit the db and pass in the settings
        PersonalSettings personalSettings = this._databaseManager.getPersonalSettings();
        setGenderIcon(personalSettings);
    }

    private void setGenderIcon(PersonalSettings personalSettings) {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.settings_screen);
        ImageView view = (ImageView) layout.findViewById(R.id.gender_field);

        if(personalSettings.getGender() == 'M'){
//            view.setImageDrawable(R.drawable.gender_selection_is_male);
        } else if(personalSettings.getGender() == 'F'){
//            view.setImageDrawable(R.drawable.)gender_selection_is_female;
        }

    }

    public void goBack(View view){
        this.finish();
    }

    public void submitPersonalSettings(View view) {

    }
}


