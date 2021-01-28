package com.caveryschool.waistwatcher;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class EnterWeightActivity extends AppCompatActivity {
    private DatabaseManager _databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterweight);
        this._databaseManager = new DatabaseManager(this);
    }

    public void goBack(View view){
        this.finish();
    }

    public void submitWeight(View view) {
        // get the value from the edit text box
        EditText weightField = findViewById(R.id.input_weight);
        String unParsedWeight = weightField.getText().toString();
        Float weightValue = Float.parseFloat(unParsedWeight);

        Date date = new Date();


        // create the weight object
        Weight weight = new Weight(weightValue, 20210128);

        // insert it into the database
        this._databaseManager.insert(weight);
    }
}