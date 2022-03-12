package com.example.assignment7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BookmarksDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "bookmarkedLocations";
    private static final String ID_COL = "id";
    private static final String LATITUDE_COL = "latitude";
    private static final String LONGITUDE_COL = "Longitude";
    public static final String ADDRESS_COL = "addressLine";
    public static final String LOCALITY_COL = "locality";






    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LATITUDE_COL + " TEXT,"
                + LONGITUDE_COL + " TEXT,"
                + ADDRESS_COL + " TEXT,"
                + LOCALITY_COL + " TEXT) ";
        db.execSQL(query);

    }

    public void addNewLocation(String latitude, String longitude, String addressLine, String locality){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LATITUDE_COL, latitude);
        values.put(LONGITUDE_COL, longitude);
        values.put(ADDRESS_COL, addressLine);
        values.put(LOCALITY_COL, locality);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<MClass> readLocations(){
        SQLiteDatabase db = this. getReadableDatabase();
        Cursor cursorLocations = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<MClass> locationArrayList = new ArrayList<>();

        if (cursorLocations.moveToFirst()){
            do {
                locationArrayList.add(new MClass(cursorLocations.getString(1),
                        cursorLocations.getString(2),
                        cursorLocations.getString(3),
                        cursorLocations.getString(4)));
            }while (cursorLocations.moveToNext());
        }
        cursorLocations.close();
        return locationArrayList;
    }

    public void deleteLocation(String address){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "addressLine=?", new String[]{address});
        db.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
