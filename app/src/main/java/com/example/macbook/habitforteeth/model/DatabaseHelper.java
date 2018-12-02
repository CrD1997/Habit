package com.example.macbook.habitforteeth.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.macbook.habitforteeth.model.Star;


public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "habit", null, 1);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists "
                + "star"
                + "(id "
                + "integer primary key,"
                + "week"
                + " integer,"
                + "day"
                + " integer,"
                + "number"
                + " integer)");
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(Star s) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id",s.getId());
        cv.put("week", s.getWeek());
        cv.put("day",s.getDay());
        cv.put("number", s.getNumber());
        database.insert("star", null, cv);
    }
    public void update(Star s){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id",s.getId());
        cv.put("week", s.getWeek());
        cv.put("day",s.getDay());
        cv.put("number", s.getNumber());
        database.update("star",cv,"id=?",new String[]{s.getId()+""});
    }
    public Cursor getAllData() {
        SQLiteDatabase database = getWritableDatabase();
        return database.query("star", null, null, null, null, null, "id" + " ASC");
    }


}
