package com.caveryschool.waistwatcher;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class HistoryClassicActivity extends AppCompatActivity {
    private DatabaseManager _databaseManager;
    private static final int TEAL = 0xFF018786;
    private Bitmap _trashCan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historyclassic);
        this._databaseManager = new DatabaseManager(this);
        this._trashCan = BitmapFactory.decodeResource(getResources(), R.drawable.trash_can);
        populateHistoryList();
    }

    public void goBack(View view) {
        Intent mainScreen = new Intent(this,MainActivity.class);
        this.startActivity(mainScreen);
    }

    public void populateHistoryList() {
        // get the layout
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.history_table);

        // create the history rows
        List<Weight> weights = this._databaseManager.selectAll();

        // insert the history row into the layout
        int previousID = R.id.history_screen_heading;
        Typeface tf = Typeface.createFromAsset(getAssets(), "myfont_regular.ttf");


        for (int i=0; i < weights.size(); i++){
            RelativeLayout row = new RelativeLayout(this);
            int rowId = 12345 + i;
            row.setId(rowId);
            TextView columnOne = new TextView(this);
            int columnOneId = View.generateViewId();
            Float weightUnformatted = weights.get(i).getWeight();
            Integer date = weights.get(i).getCreatedOnDate();
            columnOne.setTypeface(tf);
            columnOne.setTextSize(15);
            columnOne.setTextColor(TEAL);
            columnOne.setText(" " + String.valueOf(weightUnformatted) + " lbs" + "   " + String.valueOf(date));
            columnOne.setId(columnOneId);

            ImageView columnTwo = new ImageView(this);
            RelativeLayout.LayoutParams paramsForColumn2= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,75);
            paramsForColumn2.addRule(RelativeLayout.RIGHT_OF, columnOneId);
            columnTwo.setImageBitmap(this._trashCan);
            columnTwo.setLayoutParams(paramsForColumn2);
            columnTwo.setTag(weights.get(i).getID());
            columnTwo.setOnClickListener((View v) -> {
                int weightId = Integer.parseInt(v.getTag().toString());
                _databaseManager.deleteWeightEntry(weightId);
                Intent reloadHistory = new Intent(this,HistoryClassicActivity.class);
                this.startActivity(reloadHistory);
            });

            RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW, previousID);
            row.setLayoutParams(params);

            row.addView(columnOne);
            row.addView(columnTwo);

            layout.addView(row);
            previousID = rowId;
        }

    }
}
