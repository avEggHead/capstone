package com.caveryschool.waistwatcher;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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
        String sqlCreate = "create table " + TABLE_WEIGHTS + "( " + ID;
        sqlCreate += " integer primary key autoincrememnt" + WEIGHT;
        sqlCreate += " real, " + CREATED_ON_DATE + " integer )";

        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_WEIGHTS);
        onCreate(db);
    }

    public ArrayList<Weight> selectAll(){
        String sqlQuery = "select * from " + TABLE_WEIGHTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        ArrayList<Weight> weights = new ArrayList<Weight>();
        while(cursor.moveToNext()){
            Weight weight = new Weight(cursor.getInt(0), cursor.getFloat(1), cursor.getInt(2));
            weights.add(weight);
        }

        return weights;
    }
}
