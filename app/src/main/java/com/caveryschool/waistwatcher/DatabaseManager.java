package com.caveryschool.waistwatcher;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "WeightTracker";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_WEIGHTS = "weights";
    private static final String ID = "id";
    private static final String WEIGHT = "weight";
    private static final String CREATED_ON_DATE = "created_on_date";
    private static final String CREATED_ON_TIME = "created_on_time";

    private static final String TABLE_SETTINGS = "settings";
    private static final String GOAL_WEIGHT= "goal_weight";
    private static final String GOAL_DATE = "goal_date";
    private static final String GENDER = "gender";
    private static final String HEIGHT_IN_FEET = "height_in_feet";
    private static final String HEIGHT_IN_INCHES = "height_in_inches";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate1 = "create table " + TABLE_WEIGHTS + "( " + ID;
        sqlCreate1 += " integer primary key autoincrement, " + WEIGHT;
        sqlCreate1 += " real, " + CREATED_ON_DATE + " real, " + CREATED_ON_TIME +" real )";
        
        String sqlCreate2 = "create table " + TABLE_SETTINGS + "( " + ID;
        sqlCreate2 += " integer primary key autoincrement, " + GOAL_WEIGHT;
        sqlCreate2 += " real, " + GOAL_DATE + " real, " + GENDER +" text,";
        sqlCreate2 += " real, " + HEIGHT_IN_FEET + " integer, " + HEIGHT_IN_INCHES +" integer )";

        db.execSQL(sqlCreate1);
        db.execSQL(sqlCreate2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_WEIGHTS);
        db.execSQL("drop table if exists " + TABLE_SETTINGS);
        onCreate(db);
    }

    public void clearTheDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WEIGHTS, "1", null);
    }

    public List<Weight> selectAll(){
        String sqlQuery = "select * from " + TABLE_WEIGHTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        List<Weight> weights = new ArrayList<Weight>();
        while(cursor.moveToNext()){
            Weight weight = new Weight(cursor.getFloat(1), cursor.getInt(2), cursor.getInt(3));
                    weight.setID(cursor.getInt(0));
            weights.add(weight);
        }

        return weights;
    }

    public void insertSettings(PersonalSettings personalSettings){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlInsert = "insert into " + TABLE_SETTINGS;
        sqlInsert += " values( null, " + personalSettings.getGoalWeight();
        sqlInsert += ", " + personalSettings.getGoalDate();
        sqlInsert += ", " + personalSettings.getGender();
        sqlInsert += ", " + personalSettings.getHeightInFeet();
        sqlInsert += ", " + personalSettings.getHeightInInches() + " )";

        db.execSQL(sqlInsert);
        db.close();
    }

    public void insertWeight(Weight weight){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlInsert = "insert into " + TABLE_WEIGHTS;
        sqlInsert += " values( null, " + weight.getWeight();
        sqlInsert += ", " + weight.getCreatedOnDate();
        sqlInsert += ", " + weight.getCreatedOnTime() + " )";

        db.execSQL(sqlInsert);
        db.close();
    }
}
