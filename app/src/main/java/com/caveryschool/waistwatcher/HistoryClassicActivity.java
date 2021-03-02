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
import android.os.Environment;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
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

            // column one is the weight and date
            columnOne.setTypeface(tf);
            columnOne.setTextSize(25);
            columnOne.setTextColor(TEAL);
            columnOne.setText(" " + String.valueOf(weightUnformatted) + " lbs" + "   " + String.valueOf(date));
            columnOne.setId(columnOneId);

            // column two is the image
            ImageView columnTwo = new ImageView(this);
            RelativeLayout.LayoutParams paramsForColumn2= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,75);
            paramsForColumn2.addRule(RelativeLayout.RIGHT_OF, columnOneId);
            int columnTwoId = columnOneId + 10000;
            columnTwo.setId(columnTwoId);
            columnTwo.setLayoutParams(paramsForColumn2);
            File weightTrackingImageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)+ "/WAIST_WATCHER_IMAGES/" + weights.get(i).getImageId() + ".png");
            Bitmap weightTrackingBitmap = BitmapFactory.decodeFile(weightTrackingImageFile.getAbsolutePath());
            columnTwo.setImageBitmap(weightTrackingBitmap);

            // column three is the trash can
            ImageView columnThree = new ImageView(this);
            RelativeLayout.LayoutParams paramsForColumn3= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,75);
            if(weightTrackingBitmap != null){
                paramsForColumn3.addRule(RelativeLayout.RIGHT_OF, columnTwoId);
            } else{
                paramsForColumn3.addRule(RelativeLayout.RIGHT_OF, columnOneId);
            }
            columnThree.setImageBitmap(this._trashCan);
            columnThree.setLayoutParams(paramsForColumn3);
            columnThree.setTag(weights.get(i).getID());
            columnThree.setOnClickListener((View v) -> {
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
            row.addView(columnThree);

            layout.addView(row);
            previousID = rowId;
        }

    }
}
