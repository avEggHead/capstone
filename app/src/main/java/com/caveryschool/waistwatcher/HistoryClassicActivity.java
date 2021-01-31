package com.caveryschool.waistwatcher;

import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class HistoryClassicActivity extends AppCompatActivity {
    private DatabaseManager _databaseManager;
    private static final int TEAL = 0xFF018786;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historyclassic);
        this._databaseManager = new DatabaseManager(this);
        populateHistoryList();
    }

    public void goBack(View view) { this.finish();
    }

    public void populateHistoryList() {
        // get the layout
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.history_table);

        // create the history rows
        List<Weight> weights = this._databaseManager.selectAll();

        // insert the history row into the layout
        int previousID = -5000;
        AssetManager manager = getAssets();
        Typeface tf = Typeface.createFromAsset(getAssets(), "myfont_regular.ttf");

        for (int i=0; i < weights.size(); i++){
            TextView row = new TextView(this);
            Float weightUnformatted = weights.get(i).getWeight();
            Integer date = weights.get(i).getCreatedOnDate();
            row.setTypeface(tf);
            row.setTextSize(15);
            row.setTextColor(TEAL);
            row.setText(" " + String.valueOf(weightUnformatted) + " lbs" + "   " + String.valueOf(date));
            row.setId(View.generateViewId());

            RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            if(i != 0){
                params.addRule(RelativeLayout.BELOW, previousID);
            }

            row.setLayoutParams(params);
            layout.addView(row);
            previousID = row.getId();
        }

    }
}
