package com.caveryschool.waistwatcher;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void submitWeight(View view) {
        // get the value from the edit text box
        EditText weightField = findViewById(R.id.input_weight);
        String unParsedWeight = weightField.getText().toString();
        Float weightValue = Float.parseFloat(unParsedWeight);

        Calendar calendar = Calendar.getInstance(Locale.US);
        Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

        Date date = new Date();
        String dateStamp = String.valueOf(timestamp.toString()).substring(0,10).replace("-", "");
        String instantStamp = String.valueOf(timestamp.toString()).substring(11,19).replace(":","");

        // create the weight object
        Weight weight = new Weight(weightValue, Integer.parseInt(dateStamp), Integer.parseInt(instantStamp));

        // insert it into the database
        this._databaseManager.insertWeight(weight);

        this.finish();
    }

    public void tempDelete(View view) {
        this._databaseManager.clearTheDatabase();
    }
}