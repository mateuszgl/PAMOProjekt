package com.example.pamo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.example.pamo.entities.Location;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "locations";
    private static final String TABLE_NAME = "locations";
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE LOCATIONS (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE, latitude REAL, longitude REAL, description TEXT)";
    private static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS LOCATIONS";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM LOCATIONS";
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_LATITUDE = "LATITUDE";
    private static final String COLUMN_LONGITUDE = "LONGITUDE";
    private static final String COLUMN_DESCRIPTION = "DESCRIPTION";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_QUERY);
        onCreate(db);
    }


    public void insert(Location location) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, location.getName());
        contentValues.put(COLUMN_LATITUDE, location.getLatitude());
        contentValues.put(COLUMN_LONGITUDE, location.getLongitude());
        contentValues.put(COLUMN_DESCRIPTION, location.getDescrition());

        db.insert(TABLE_NAME, null, contentValues);
    }

    public ArrayList<String> getAllNames(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor data = db.rawQuery(SELECT_ALL_QUERY, null);

        ArrayList<String> locations = new ArrayList<>();

        while(data.moveToNext()){
            locations.add(data.getString(1));
        }
        data.close();

        return locations;
    }

    public Location getByName(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Location location = null;

        Cursor data = db.rawQuery("SELECT * FROM LOCATIONS WHERE NAME = \"" + name +"\"", null);
        if (data.moveToFirst()) {
            location = new Location();
            location.setName(data.getString(1));
            location.setDescrition(data.getString(4));
            location.setLatitude(data.getDouble(2));
            location.setLongitude(data.getDouble(3));
        }
        data.close();

        return location;
    }

    public void deleteLocation(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "NAME=?" ,
                new String[] { name });
    }
}
