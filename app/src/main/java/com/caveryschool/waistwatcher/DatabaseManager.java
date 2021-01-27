package com.caveryschool.waistwatcher;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "WeightTracker";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_WEIGHTS = "weights";
    private static final String ID = "id";
    private static final String WEIGHT = "weight";
    private static final String CREATED_ON_DATE = "created_on_date";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "create table " + TABLE_WEIGHTS + "( ";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
