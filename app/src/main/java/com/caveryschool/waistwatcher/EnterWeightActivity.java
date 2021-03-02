package com.caveryschool.waistwatcher;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private String _imageIdentifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterweight);
        this._databaseManager = new DatabaseManager(this);
        this.imageView = (ImageView) findViewById(R.id.picture_container);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    public void goBack(View view){
        this.finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void submitWeight(View view) {
        // get the value from the edit text box
        EditText weightField = findViewById(R.id.input_weight);
        String unParsedWeight = weightField.getText().toString();
        if(unParsedWeight.length() > 0){
            Float weightValue = Float.parseFloat(unParsedWeight);

            Calendar calendar = Calendar.getInstance(Locale.US);
            Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

            Date date = new Date();
            String dateStamp = String.valueOf(timestamp.toString()).substring(0,10).replace("-", "");
            String instantStamp = String.valueOf(timestamp.toString()).substring(11,19).replace(":","");

            // create the weight object
            Weight weight = new Weight(weightValue, Integer.parseInt(dateStamp), Integer.parseInt(instantStamp), this._imageIdentifier);

            // insert it into the database
            this._databaseManager.insertWeight(weight);

            this.finish();
        } else{

        }

    }

    public void clearAll(View view){

        EditText weightField = findViewById(R.id.input_weight);
        weightField.setText("");
        imageView.setImageBitmap(null);
    }

    public void tempDelete(View view) {
        this._databaseManager.clearTheDatabase();
    }

    public void takePicture(View view) {
        PackageManager manager = this.getPackageManager();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        if(manager.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            this._imageIdentifier = java.util.UUID.randomUUID().toString();
            File fileLocation = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "WAIST_WATCHER_IMAGES");
            File actualFile = new File(fileLocation, this._imageIdentifier + ".jpg");
//            Uri image = FileProvider.getUriForFile(
//                    this,
//                  "com.caveryschool.waistwatcher.provider",
//                  location
//            );

//            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(actualFile));
            takePictureIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivityForResult(takePictureIntent, PHOTO_REQUEST);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PHOTO_REQUEST){
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(bitmap);
            imageView.setTag(this._imageIdentifier);
            File fileLocation = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "WAIST_WATCHER_IMAGES");
            File actualFile = new File(fileLocation, this._imageIdentifier + ".png");
            if (!fileLocation.exists()) {
                fileLocation.mkdir();
            }
            if (!actualFile.exists()) {
                try {
                    actualFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try (FileOutputStream out = new FileOutputStream(actualFile)) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}