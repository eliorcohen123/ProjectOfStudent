package com.tomer.myplaces.DataPck;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tomer.myplaces.DataPck.Geometry;
import com.tomer.myplaces.DataPck.Location;
import com.tomer.myplaces.DataPck.Photos;
import com.tomer.myplaces.DataPck.PlaceModel;

import java.util.ArrayList;
import java.util.List;

public class PlaceDBHelperFavorites extends SQLiteOpenHelper {

    private static final String PLACE_TABLE_NAME = "FAVORITES";
    private static final String PLACE_ID = "ID";
    private static final String PLACE_NAME = "NAME";
    private static final String PLACE_ADDRESS = "ADDRESS";
    private static final String PLACE_RATING = "RATING";
    private static final String PLACE_KM = "KM";
    private static final String PLACE_PHOTOS = "PHOTOS";

    public PlaceDBHelperFavorites(Context context) {
        super(context, PLACE_TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + PLACE_TABLE_NAME + "(" +
                PLACE_ID + " INTEGER PRIMARY KEY, " +
                PLACE_NAME + " TEXT, " +
                PLACE_ADDRESS + " TEXT, " +
                PLACE_RATING + " REAL, " +
                PLACE_KM + " REAL, " +
                PLACE_PHOTOS + " TEXT " + ")";
        try {
            db.execSQL(CREATE_TABLE);
        } catch (SQLiteException ex) {
            Log.e("SQLiteException", ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PLACE_TABLE_NAME);
        onCreate(db);
    }

    //Add info items
    public void addMapFav(String name, String address, Double rating, Double km, String photo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(PLACE_NAME, name);
        contentValues.put(PLACE_ADDRESS, address);
        contentValues.put(PLACE_RATING, rating);
        contentValues.put(PLACE_KM, km);
        contentValues.put(PLACE_PHOTOS, photo);

        long id = db.insertOrThrow(PLACE_TABLE_NAME, null, contentValues);
        try {
            Log.d("PlaceDBHelperFavorites", "insert new place with id: " + id +
                    ", name: " + name);
        } catch (SQLiteException ex) {
            Log.e("PlaceDBHelperFavorites", ex.getMessage());
        } finally {
            db.close();
        }
    }

    // Get all info items
    public ArrayList<PlaceModel> getAllPlaces() {
        ArrayList<PlaceModel> placeModels = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(PLACE_TABLE_NAME, null, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int colID = cursor.getColumnIndex(PLACE_ID);
            int id = cursor.getInt(colID);
            String name = cursor.getString(1);
            String address = cursor.getString(2);
            double rating = cursor.getDouble(3);
            String km = cursor.getString(4);
            String photo = cursor.getString(5);
            Photos photos = new Photos();
            photos.setPhoto_reference(photo);
            List<Photos> photosList = new ArrayList<Photos>();
            photosList.add(photos);
            PlaceModel placeModel = new PlaceModel(name, address, rating, km, photosList);
            placeModel.setId(String.valueOf(id));
            placeModels.add(placeModel);
        }
        return placeModels;
    }

}
