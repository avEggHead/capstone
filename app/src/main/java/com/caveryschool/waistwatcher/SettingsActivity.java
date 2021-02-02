package com.caveryschool.waistwatcher;

import android.app.Person;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private DatabaseManager _databaseManager;
    private RelativeLayout _layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this._layout = (RelativeLayout) findViewById(R.id.settings_screen);
        this._databaseManager = new DatabaseManager(this);
        setPersonalSettings();
    }

    private void setPersonalSettings() {
        PersonalSettings personalSettings = this._databaseManager.getPersonalSettings();
        EditText goalWeight = (EditText) this._layout.findViewById(R.id.input_goal_weight);
        setGenderIcon(personalSettings);
    }

    private void setGenderIcon(PersonalSettings personalSettings) {
        ImageView view = (ImageView) this._layout.findViewById(R.id.gender_field);

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


