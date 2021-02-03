package com.caveryschool.waistwatcher;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "WeightTracker";
    private static final int DATABASE_VERSION = 3;
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
        String sqlCreateWeightsTable = "create table " + TABLE_WEIGHTS + "( " + ID;
        sqlCreateWeightsTable += " integer primary key autoincrement, " + WEIGHT;
        sqlCreateWeightsTable += " real, " + CREATED_ON_DATE + " real, " + CREATED_ON_TIME +" real )";
        
        String sqlCreateSettingsTable = "create table " + TABLE_SETTINGS + "( " + ID;
        sqlCreateSettingsTable += " integer primary key autoincrement, " + GOAL_WEIGHT;
        sqlCreateSettingsTable += " real, " + GOAL_DATE + " real, " + GENDER +" text,";
        sqlCreateSettingsTable += " real, " + HEIGHT_IN_FEET + " integer, " + HEIGHT_IN_INCHES +" integer )";

        db.execSQL(sqlCreateWeightsTable);
        db.execSQL(sqlCreateSettingsTable);
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

    public PersonalSettings getPersonalSettings() {
        String sqlQuery = "select * from " + TABLE_SETTINGS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);
        PersonalSettings personalSettings = new PersonalSettings();
        setDefaults(personalSettings);
        if (cursor.getCount() > 0){
            personalSettings.setGoalWeight(cursor.getFloat(1));
            personalSettings.setGoalDate(cursor.getInt(2));
            personalSettings.setGender(cursor.getString(3).charAt(0));
        }
        return  personalSettings;
    }

    private void setDefaults(PersonalSettings personalSettings) {
        personalSettings.setGender('F');
        personalSettings.setGoalDate(20220101);
        personalSettings.setGoalWeight((float)185.00);
        personalSettings.setHeightInFeet(5);
        personalSettings.setHeightInInches(9);
    }

    public void upsertPersonalSettings(PersonalSettings newSettings) {
        // check the database
        PersonalSettings settingsInDatabase = this.getPersonalSettings();

        // if it's empty insert
        if(settingsInDatabase.getId() == 0){
            insertSettings(newSettings);
        } else{
            // if it has settings, use the same Id and update
            updateSettings(settingsInDatabase, newSettings);
        }
    }

    private void updateSettings(PersonalSettings settingsInDatabase, PersonalSettings newSettings) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlUpdateQuery = "UPDATE " + TABLE_SETTINGS;
        sqlUpdateQuery += "SET " + GOAL_WEIGHT + " = " + newSettings.getGoalWeight();
        sqlUpdateQuery += "SET " + GOAL_DATE + " = " + newSettings.getGoalDate();
        sqlUpdateQuery += "SET " + GENDER + " = " + newSettings.getGender();
        sqlUpdateQuery += "SET " + HEIGHT_IN_FEET + " = " + newSettings.getHeightInFeet();
        sqlUpdateQuery += "SET " + HEIGHT_IN_INCHES + " = " + newSettings.getHeightInInches();
        sqlUpdateQuery += "WHERE " + ID + " = " + settingsInDatabase.getId();

        db.execSQL(sqlUpdateQuery);
    }
}
