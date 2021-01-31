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

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "create table " + TABLE_WEIGHTS + "( " + ID;
        sqlCreate += " integer primary key autoincrement, " + WEIGHT;
        sqlCreate += " real, " + CREATED_ON_DATE + " real, " + CREATED_ON_TIME +" real )";

        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_WEIGHTS);
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

    public void insert(Weight weight){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlInsert = "insert into " + TABLE_WEIGHTS;
        sqlInsert += " values( null, " + weight.getWeight();
        sqlInsert += ", " + weight.getCreatedOnDate();
        sqlInsert += ", " + weight.getCreatedOnTime() + " )";

        db.execSQL(sqlInsert);
        db.close();
    }
}
