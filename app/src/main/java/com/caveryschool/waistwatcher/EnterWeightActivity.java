package com.caveryschool.waistwatcher;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
    private static final int PHOTO_REQUEST = 1;
    private Bitmap bitmap;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterweight);
        this._databaseManager = new DatabaseManager(this);
        this.imageView = (ImageView) findViewById(R.id.picture_container);
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

    public void clearAll(View view){

        EditText weightField = findViewById(R.id.input_weight);
        weightField.setText("");
    }

    public void tempDelete(View view) {
        this._databaseManager.clearTheDatabase();
    }

    public void takePicture(View view) {
        PackageManager manager = this.getPackageManager();
        if(manager.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePictureIntent, PHOTO_REQUEST);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PHOTO_REQUEST && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(bitmap);
        }
    }
}