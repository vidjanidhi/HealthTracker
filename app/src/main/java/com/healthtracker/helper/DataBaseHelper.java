package com.healthtracker.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by local nidhi on 23-04-2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "healthTracker";

    //tables
    private static final String TABLE_USER = "user";
    private static final String TABLE_WEIGHT = "weight";

    // table user

    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_FIRST_NAME = "user_name";

    private static final String KEY_GENDER = "gender";
    private static final String KEY_AGE = "age";
    private static final String KEY_HEIGHT = "height";

    //CREATE WEIGHT
    private static final String KEY_ROWID = "rowid";


    //CREATE USER
    private static final String TABLE_CREATE_USER = "create table "
            + TABLE_USER + "( "
            + KEY_USER_ID + " integer primary key autoincrement,"
            + KEY_FIRST_NAME + " text not null,"
            + KEY_GENDER + " text not null"
            + KEY_AGE + "integer"
            + KEY_HEIGHT + "integer"
            + ");";


    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_FAT = "fat";
    private static final String KEY_ABDOMEN = "Abdomen";
    private static final String KEY_WAIST = "waist";
    private static final String KEY_HIP = "hip";
    private static final String TABLE_CREATE_WEIGHT = "create table "
            + TABLE_WEIGHT + "( "
            + KEY_ROWID + " integer primary key autoincrement,"
            + KEY_USER_ID + " integer,"
            + KEY_WEIGHT + " text not null"
            + KEY_FAT + "integer"
            + KEY_ABDOMEN + "integer"
            + KEY_WAIST + "integer"
            + KEY_HIP + " integer" +
            ");";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
