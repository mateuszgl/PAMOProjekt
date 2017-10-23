package com.example.pamo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import com.example.pamo.entities.Location;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "locations";
    private static final String TABLE_NAME = "locations";
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE LOCATIONS (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, latitude REAL, longitude REAL, description TEXT)";
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
    }


    public void insert(Location location) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, location.getName());
        contentValues.put(COLUMN_LATITUDE, location.getLatitude());
        contentValues.put(COLUMN_LONGITUDE, location.getLongitude());
        contentValues.put(COLUMN_DESCRIPTION, location.getDescrition());

        long result = db.insert(TABLE_NAME, null, contentValues);
    }

    public List<Location> getAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor data = db.rawQuery(SELECT_ALL_QUERY, null);
        data.moveToFirst();

        List<Location> locations = new ArrayList<>();

        while(data.moveToNext()){
            Location location = new Location();
            location.setName(data.getString(1));
            locations.add(0,location);
        }
        data.close();

        return locations;
    }

//    /**
//     * Returns only the ID that matches the name passed in
//     * @param name
//     * @return
//     */
//    public Cursor getByName(String name){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "SELECT * FROM LOCATIONS WHERE name='" + name + "'";
//        Cursor data = db.rawQuery(query, null);
//        return data;
//    }
//
//    /**
//     * Updates the name field
//     * @param newName
//     * @param id
//     * @param oldName
//     */
//    public void updateName(String newName, int id, String oldName){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
//                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
//                " AND " + COL2 + " = '" + oldName + "'";
//        Log.d(TAG, "updateName: query: " + query);
//        Log.d(TAG, "updateName: Setting name to " + newName);
//        db.execSQL(query);
//    }
//
//    /**
//     * Delete from database
//     * @param id
//     * @param name
//     */
//    public void deleteName(int id, String name){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
//                + COL1 + " = '" + id + "'" +
//                " AND " + COL2 + " = '" + name + "'";
//        db.execSQL(query);
//    }
}
