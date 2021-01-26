package com.caveryschool.waistwatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToEnterWeight(View view){
        Intent enterWeightScreen = new Intent(this,EnterWeightActivity.class);
        this.startActivity(enterWeightScreen);
    }

    public void goToViewHistory(View view) {
    }

    public void goToSettings(View view) {
    }
}