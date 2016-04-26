package com.healthtracker.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.healthtracker.model.User;

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
    private static final String TABLE_WEIGHT = "weight_fragment";
    private static final String TABLE_GLUCOSE = "fat";
    private static final String TABLE_B_P = "abdomen";

    private static final String TABLE_CHOLESEROL = "cholesterol";
    private static final String TABLE_THYROID = "thyroid";

    // table user

    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_FIRST_NAME = "user_name";

    private static final String KEY_GENDER = "gender";
    private static final String KEY_AGE = "age";
    private static final String KEY_HEIGHT = "height";


    //CREATE USER
    private static final String TABLE_CREATE_USER = "create table "
            + TABLE_USER + "( "
            + KEY_USER_ID + " integer primary key autoincrement,"
            + KEY_FIRST_NAME + " text not null,"
            + KEY_GENDER + " text not null"
            + KEY_AGE + "integer"
            + KEY_HEIGHT + "integer"
            + ");";


    //CREATE WEIGHT
    private static final String KEY_ROWID = "rowid";
    private static final String KEY_WEIGHT = "weight_fragment";
    private static final String KEY_FAT = "fat";
    private static final String KEY_ABDOMEN = "abdomen";
    private static final String KEY_WAIST = "waist";
    private static final String KEY_HIP = "hip";

    private static final String TABLE_CREATE_WEIGHT = "create table "
            + TABLE_WEIGHT + "( "
            + KEY_ROWID + " integer primary key autoincrement,"
            + KEY_USER_ID + " integer,"
            + KEY_WEIGHT + " text not null"
            + KEY_FAT + "text"
            + KEY_ABDOMEN + "text"
            + KEY_WAIST + "text"
            + KEY_HIP + " text" +
            ");";

    // create glucose
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_GLUCOSE = "glucose";
    private static final String KEY_HBA1C = "hba1c";
    private static final String TABLE_CREATE_GLUCOSE = "create table "
            + TABLE_GLUCOSE + "( "
            + KEY_ROWID + " integer primary key autoincrement,"
            + KEY_USER_ID + " integer,"
            + KEY_CATEGORY + "integer"
            + KEY_GLUCOSE + " text"
            + KEY_HBA1C + "text"
            + ");";

    //create  B.P

    private static final String KEY_SYSTOLIC = "systolic";
    private static final String KEY_DIASTOLIC = "diastolic";
    private static final String KEY_HEART_RATE = "heart_rate";
    private static final String TABLE_CREATE_B_P = "create table "
            + TABLE_B_P + "( "
            + KEY_ROWID + " integer primary key autoincrement,"
            + KEY_USER_ID + " integer,"
            + KEY_SYSTOLIC + " text"
            + KEY_DIASTOLIC + " text"
            + KEY_HEART_RATE + "text"
            + ");";

    //create cholestrol

    private static final String KEY_HDL = "hdl";
    private static final String KEY_LDL = "ldl";
    private static final String KEY_TOTAL = "total";
    private static final String KEY_TRIGLYCERIDES = "triglycerides";
    private static final String TABLE_CREATE_CHOLESEROL = "create table "
            + TABLE_CHOLESEROL + "( "
            + KEY_ROWID + " integer primary key autoincrement,"
            + KEY_USER_ID + " integer,"
            + KEY_HDL + " text"
            + KEY_LDL + " text"
            + KEY_TOTAL + " text"
            + KEY_TRIGLYCERIDES + " text"
            + ");";

    //create thyroid

    private static final String KEY_TSH_LEVEL = "tsh_level";
    private static final String TABLE_CREATE_THYROID = "create table "
            + TABLE_THYROID + "( "
            + KEY_ROWID + " integer primary key autoincrement,"
            + KEY_USER_ID + " integer,"
            + KEY_TSH_LEVEL + "text"
            + ");";

    private static final String DROP_TABLE_USER = "DROP TABLE IF EXISTS " + TABLE_USER;
    private static final String DROP_TABLE_WEIGHT = "DROP TABLE IF EXISTS " + TABLE_WEIGHT;
    private static final String DROP_TABLE_GLUCOSE = "DROP TABLE IF EXISTS " + TABLE_GLUCOSE;
    private static final String DROP_TABLE_B_P = "DROP TABLE IF EXISTS " + TABLE_B_P;
    private static final String DROP_TABLE_CHOLESEROL = "DROP TABLE IF EXISTS " + TABLE_CHOLESEROL;
    private static final String DROP_TABLE_THYROID = "DROP TABLE IF EXISTS " + TABLE_THYROID;


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_USER);
        db.execSQL(TABLE_CREATE_WEIGHT);
        db.execSQL(TABLE_CREATE_GLUCOSE);
        db.execSQL(TABLE_CREATE_B_P);
        db.execSQL(TABLE_CREATE_CHOLESEROL);
        db.execSQL(TABLE_CREATE_THYROID);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_USER);
        db.execSQL(DROP_TABLE_WEIGHT);
        db.execSQL(DROP_TABLE_GLUCOSE);
        db.execSQL(DROP_TABLE_B_P);
        db.execSQL(DROP_TABLE_CHOLESEROL);
        db.execSQL(DROP_TABLE_THYROID);
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, user.getUserId());
        values.put(KEY_FIRST_NAME, user.getUserName());
        values.put(KEY_AGE, user.getAge());
        values.put(KEY_GENDER, user.getGender());
        values.put(KEY_HEIGHT, user.getHeight());

        db.insert(TABLE_USER, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, user.getUserId());
        values.put(KEY_FIRST_NAME, user.getUserName());
        values.put(KEY_AGE, user.getAge());
        values.put(KEY_GENDER, user.getGender());
        values.put(KEY_HEIGHT, user.getHeight());
        return db.update(TABLE_USER, values, KEY_USER_ID + " = ?",
                new String[]{String.valueOf(user.getUserId())});

    }

    // Deleting single contact
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_USER_ID + " = ?",
                new String[]{String.valueOf(user.getUserId())});
        db.close();

    }
}
