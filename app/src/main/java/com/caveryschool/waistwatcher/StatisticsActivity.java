package com.caveryschool.waistwatcher;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class StatisticsActivity extends AppCompatActivity {
    private DatabaseManager _databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        this._databaseManager = new DatabaseManager(this);
    }
}
