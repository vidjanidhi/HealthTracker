package com.healthtracker.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.healthtracker.model.BloodPresure;
import com.healthtracker.model.Glucose;
import com.healthtracker.model.Log;
import com.healthtracker.model.Thyroid;
import com.healthtracker.model.User;
import com.healthtracker.model.Weight;
import com.healthtracker.model.cholesterol;

import java.util.ArrayList;

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
    private static final String TABLE_GLUCOSE = "glucose";
    private static final String TABLE_B_P = "bp";
    private static final String TABLE_CHOLESEROL = "cholesterol";
    private static final String TABLE_THYROID = "thyroid";
    private static final String TABLE_LOG = "log";
    private static final String TABLE_ACTIVITY = "activity";
    private static final String TABLE_FOOD = "food";
    private static final String TABLE_MIS = "mis";

    // table user

    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_FIRST_NAME = "user_name";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_AGE = "age";
    private static final String KEY_HEIGHT = "height";

    //CREATE USER
    private static final String TABLE_CREATE_USER = "CREATE TABLE IF NOT EXISTS "
            + TABLE_USER + "( "
            + KEY_USER_ID + " integer primary key autoincrement, "
            + KEY_FIRST_NAME + " text not null, "
            + KEY_GENDER + " INTEGER, "
            + KEY_AGE + " INTEGER, "
            + KEY_HEIGHT + " INTEGER "
            + ");";


    //CREATE WEIGHT
    private static final String KEY_ROWID = "rowid";
    private static final String KEY_WEIGHT = "weight_fragment";
    private static final String KEY_FAT = "fat";
    private static final String KEY_ABDOMEN = "abdomen";
    private static final String KEY_WAIST = "waist";
    private static final String KEY_HIP = "hip";
    private static final String KEY_NOTE = "note";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";


    private static final String TABLE_CREATE_WEIGHT = "CREATE TABLE IF NOT EXISTS "
            + TABLE_WEIGHT + "( "
            + KEY_ROWID + " integer primary key autoincrement,"
            + KEY_USER_ID + " INTEGER,"
            + KEY_WEIGHT + " text,"
            + KEY_FAT + " text,"
            + KEY_ABDOMEN + " text,"
            + KEY_WAIST + " text,"
            + KEY_NOTE + " text,"
            + KEY_DATE + " text,"
            + KEY_TIME + " text,"
            + KEY_HIP + " text" +
            ");";

    // create glucose
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_GLUCOSE = "glucose";
    private static final String KEY_HBA1C = "hba1c";
    private static final String KEY_TESTING_TIME = "testing_time";
    private static final String TABLE_CREATE_GLUCOSE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_GLUCOSE + "( "
            + KEY_ROWID + " integer primary key autoincrement,"
            + KEY_USER_ID + " INTEGER,"
            + KEY_CATEGORY + " INTEGER,"
            + KEY_GLUCOSE + " text,"
            + KEY_NOTE + " text,"
            + KEY_DATE + " text,"
            + KEY_TIME + " text,"
            + KEY_HBA1C + " text,"
            + KEY_TESTING_TIME + " text"
            + ");";

    //create  B.P

    private static final String KEY_SYSTOLIC = "systolic";
    private static final String KEY_DIASTOLIC = "diastolic";
    private static final String KEY_HEART_RATE = "heart_rate";
    private static final String TABLE_CREATE_B_P = "CREATE TABLE IF NOT EXISTS "
            + TABLE_B_P + "( "
            + KEY_ROWID + " integer primary key autoincrement,"
            + KEY_USER_ID + " INTEGER,"
            + KEY_SYSTOLIC + " text,"
            + KEY_DIASTOLIC + " text,"
            + KEY_NOTE + " text,"
            + KEY_DATE + " text,"
            + KEY_TIME + " text,"
            + KEY_HEART_RATE + " text"
            + ");";

    //create cholestrol

    private static final String KEY_HDL = "hdl";
    private static final String KEY_LDL = "ldl";
    private static final String KEY_TOTAL = "total";
    private static final String KEY_TRIGLYCERIDES = "triglycerides";
    private static final String TABLE_CREATE_CHOLESEROL = "CREATE TABLE IF NOT EXISTS "
            + TABLE_CHOLESEROL + "( "
            + KEY_ROWID + " integer primary key autoincrement,"
            + KEY_USER_ID + " INTEGER,"
            + KEY_HDL + " text,"
            + KEY_LDL + " text,"
            + KEY_TOTAL + " text,"
            + KEY_NOTE + " text,"
            + KEY_DATE + " text,"
            + KEY_TIME + " text,"
            + KEY_TRIGLYCERIDES + " text"
            + ");";

    //create thyroid

    private static final String KEY_TSH_LEVEL = "tsh_level";
    private static final String TABLE_CREATE_THYROID = "create table "
            + TABLE_THYROID + "( "
            + KEY_ROWID + " integer primary key autoincrement,"
            + KEY_USER_ID + " INTEGER,"
            + KEY_NOTE + " text,"
            + KEY_DATE + " text,"
            + KEY_TIME + " text,"
            + KEY_TSH_LEVEL + " text"
            + ");";

    private static final String KEY_UNIT = "unit";
    private static final String KEY_NAME = "name";
    private static final String KEY_LOGID = "log_id";
    private static final String TABLE_CREATE_LOG = "CREATE TABLE IF NOT EXISTS "
            + TABLE_LOG + "( "
            + KEY_ROWID + " integer primary key autoincrement,"
            + KEY_LOGID + " integer ,"
            + KEY_NAME + " text,"
            + KEY_UNIT + " text"
            + ");";


    private static final String DROP_TABLE_USER = "DROP TABLE IF EXISTS " + TABLE_USER;
    private static final String DROP_TABLE_WEIGHT = "DROP TABLE IF EXISTS " + TABLE_WEIGHT;
    private static final String DROP_TABLE_GLUCOSE = "DROP TABLE IF EXISTS " + TABLE_GLUCOSE;
    private static final String DROP_TABLE_B_P = "DROP TABLE IF EXISTS " + TABLE_B_P;
    private static final String DROP_TABLE_CHOLESEROL = "DROP TABLE IF EXISTS " + TABLE_CHOLESEROL;
    private static final String DROP_TABLE_THYROID = "DROP TABLE IF EXISTS " + TABLE_THYROID;
    private static final String DROP_TABLE_FOOD = "DROP TABLE IF EXISTS " + TABLE_FOOD;
    private static final String DROP_TABLE_LOG = "DROP TABLE IF EXISTS " + TABLE_LOG;


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
        db.execSQL(TABLE_CREATE_LOG);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_USER);
        db.execSQL(DROP_TABLE_WEIGHT);
        db.execSQL(DROP_TABLE_GLUCOSE);
        db.execSQL(DROP_TABLE_B_P);
        db.execSQL(DROP_TABLE_CHOLESEROL);
        db.execSQL(DROP_TABLE_THYROID);
        db.execSQL(DROP_TABLE_LOG);
        onCreate(db);
    }

    public int addLog(Log log) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, log.getName());
        values.put(KEY_UNIT, log.getUnit());
        values.put(KEY_LOGID, log.getLogId());

        db.insert(TABLE_LOG, null, values);
        String selectQuery = "SELECT  * FROM " + TABLE_LOG;
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToLast();
        int userId = cursor.getInt(0);

        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
        return userId;
    }

    public ArrayList<Log> getAllLog(int logId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG + " where " + KEY_LOGID + " = " + logId;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<Log> logArrayList = new ArrayList<Log>();
        if (cursor.moveToFirst()) {
            do {
                Log log = new Log();
                log.setRowId(cursor.getInt(cursor.getColumnIndex(KEY_ROWID)));
                log.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                log.setUnit(cursor.getString(cursor.getColumnIndex(KEY_UNIT)));
                logArrayList.add(log);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return logArrayList;
    }


    public int addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, user.getUserName());
        values.put(KEY_AGE, user.getAge());
        values.put(KEY_GENDER, user.getGender());
        values.put(KEY_HEIGHT, user.getHeight());

        db.insert(TABLE_USER, null, values);
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToLast();
        int userId = cursor.getInt(0);

        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
        return userId;
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

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_USER_ID + " = ?",
                new String[]{String.valueOf(user.getUserId())});
        db.close();

    }

    public long addWeight(Weight weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, weight.getUserId());
        values.put(KEY_WEIGHT, weight.getWeight());
        values.put(KEY_FAT, weight.getFat());
        values.put(KEY_ABDOMEN, weight.getAbdomen());
        values.put(KEY_WAIST, weight.getWaist());
        values.put(KEY_HIP, weight.getHips());
        values.put(KEY_NOTE, weight.getNote());
        values.put(KEY_DATE, weight.getDate());
        values.put(KEY_TIME, weight.getTime());

        return db.insert(TABLE_WEIGHT, null, values);
//        db.close();
    }

    public long updateWeight(Weight weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, weight.getUserId());
        values.put(KEY_WEIGHT, weight.getWeight());
        values.put(KEY_FAT, weight.getFat());
        values.put(KEY_ABDOMEN, weight.getAbdomen());
        values.put(KEY_WAIST, weight.getWaist());
        values.put(KEY_HIP, weight.getHips());
        values.put(KEY_NOTE, weight.getNote());
        values.put(KEY_DATE, weight.getDate());
        values.put(KEY_TIME, weight.getTime());
        return db.update(TABLE_WEIGHT, values, KEY_ROWID + " = ?",
                new String[]{String.valueOf(weight.getRowId())});
    }

    public void deleteWeight(int rowId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WEIGHT, KEY_ROWID + " = ?",
                new String[]{String.valueOf(rowId)});
        db.close();

    }

    public void deleteGlucose(int rowId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GLUCOSE, KEY_ROWID + " = ?",
                new String[]{String.valueOf(rowId)});
        db.close();

    }

    public void deleteBP(int rowId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_B_P, KEY_ROWID + " = ?",
                new String[]{String.valueOf(rowId)});
        db.close();

    }

    public void deleteCholesterol(int rowId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CHOLESEROL, KEY_ROWID + " = ?",
                new String[]{String.valueOf(rowId)});
        db.close();

    }

    public void deleteThyroid(int rowId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_THYROID, KEY_ROWID + " = ?",
                new String[]{String.valueOf(rowId)});
        db.close();

    }

    public long addGlucose(Glucose glucose) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, glucose.getUserId());
        values.put(KEY_NOTE, glucose.getNote());
        values.put(KEY_DATE, glucose.getDate());
        values.put(KEY_TIME, glucose.getTime());
        values.put(KEY_GLUCOSE, glucose.getGlucose());
        values.put(KEY_HBA1C, glucose.getHba1c());
        values.put(KEY_TESTING_TIME, glucose.getTestingTime());
        return db.insert(TABLE_GLUCOSE, null, values);

    }

    public long updateGlucose(Glucose glucose) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, glucose.getUserId());
        values.put(KEY_NOTE, glucose.getNote());
        values.put(KEY_DATE, glucose.getDate());
        values.put(KEY_TIME, glucose.getTime());
        values.put(KEY_GLUCOSE, glucose.getGlucose());
        values.put(KEY_HBA1C, glucose.getHba1c());
        values.put(KEY_TESTING_TIME, glucose.getTestingTime());
        return db.update(TABLE_GLUCOSE, values, KEY_ROWID + " = ?",
                new String[]{String.valueOf(glucose.getRowId())});
    }

    public long addCholestrol(cholesterol cholesterol) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, cholesterol.getUserId());
        values.put(KEY_NOTE, cholesterol.getNote());
        values.put(KEY_DATE, cholesterol.getDate());
        values.put(KEY_TIME, cholesterol.getTime());
        values.put(KEY_HDL, cholesterol.getHdl());
        values.put(KEY_LDL, cholesterol.getLdl());
        values.put(KEY_TOTAL, cholesterol.getTotal());
        values.put(KEY_TRIGLYCERIDES, cholesterol.getTriglyceride());
        return db.insert(TABLE_CHOLESEROL, null, values);

    }

    public long updateCholestrol(cholesterol cholesterol) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, cholesterol.getUserId());
        values.put(KEY_NOTE, cholesterol.getNote());
        values.put(KEY_DATE, cholesterol.getDate());
        values.put(KEY_TIME, cholesterol.getTime());
        values.put(KEY_HDL, cholesterol.getHdl());
        values.put(KEY_LDL, cholesterol.getLdl());
        values.put(KEY_TOTAL, cholesterol.getTotal());
        values.put(KEY_TRIGLYCERIDES, cholesterol.getTriglyceride());
        return db.update(TABLE_CHOLESEROL, values, KEY_ROWID + " = ?",
                new String[]{String.valueOf(cholesterol.getRowId())});
    }

    public long addBp(BloodPresure bloodPresure) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, bloodPresure.getUserId());
        values.put(KEY_NOTE, bloodPresure.getNote());
        values.put(KEY_DATE, bloodPresure.getDate());
        values.put(KEY_TIME, bloodPresure.getTime());
        values.put(KEY_SYSTOLIC, bloodPresure.getSystolic());
        values.put(KEY_DIASTOLIC, bloodPresure.getDiastolic());
        values.put(KEY_HEART_RATE, bloodPresure.getHeartrate());
        return db.insert(TABLE_B_P, null, values);

    }

    public long updateBp(BloodPresure bloodPresure) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, bloodPresure.getUserId());
        values.put(KEY_NOTE, bloodPresure.getNote());
        values.put(KEY_DATE, bloodPresure.getDate());
        values.put(KEY_TIME, bloodPresure.getTime());
        values.put(KEY_SYSTOLIC, bloodPresure.getSystolic());
        values.put(KEY_DIASTOLIC, bloodPresure.getDiastolic());
        values.put(KEY_HEART_RATE, bloodPresure.getHeartrate());
        return db.update(TABLE_B_P, values, KEY_ROWID + " = ?",
                new String[]{String.valueOf(bloodPresure.getRowId())});

    }

    public long addThyroid(Thyroid thyroid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, thyroid.getUserId());
        values.put(KEY_NOTE, thyroid.getNote());
        values.put(KEY_DATE, thyroid.getDate());
        values.put(KEY_TIME, thyroid.getTime());
        values.put(KEY_TSH_LEVEL, thyroid.getTshLevel());
        return db.insert(TABLE_THYROID, null, values);
    }

    public long updateThyroid(Thyroid thyroid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, thyroid.getUserId());
        values.put(KEY_NOTE, thyroid.getNote());
        values.put(KEY_DATE, thyroid.getDate());
        values.put(KEY_TIME, thyroid.getTime());
        values.put(KEY_TSH_LEVEL, thyroid.getTshLevel());
        return db.update(TABLE_THYROID, values, KEY_ROWID + " = ?",
                new String[]{String.valueOf(thyroid.getRowId())});
    }


    // Deleting single contact


    public User getUser(int userID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{KEY_USER_ID,
                        KEY_FIRST_NAME, KEY_AGE, KEY_HEIGHT, KEY_GENDER}, KEY_USER_ID + "=?",
                new String[]{String.valueOf(userID)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        User user = new User();
        user.setUserId(cursor.getInt(0));
        user.setGender(cursor.getInt(4));
        user.setUserName(cursor.getString(1));
        user.setAge(cursor.getInt(2));
        user.setHeight(cursor.getInt(3));

        return user;
    }

    public int getUserIdFromPosition(int position) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToPosition(position);
        return cursor.getInt(0);

    }

    public ArrayList<User> getAllUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<User> usersList = new ArrayList<User>();
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setUserId(cursor.getInt(0));
                user.setUserName(cursor.getString(1));
                user.setAge(cursor.getInt(3));
                user.setHeight(cursor.getInt(4));
                user.setGender(cursor.getInt(2));
                // get the data into array, or class variable
                usersList.add(user);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return usersList;
    }

    public ArrayList<Weight> getAllWeight(int userId) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_WEIGHT + " where " + KEY_USER_ID + " = " + userId;

        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<Weight> weightArrayList = new ArrayList<Weight>();
        android.util.Log.i("get arrsylist", weightArrayList.size() + "");
        if (cursor.moveToFirst()) {
            do {
                Weight weight = new Weight();
                weight.setUserId(userId);
                weight.setRowId(cursor.getInt(cursor.getColumnIndex(KEY_ROWID)));
                weight.setWeight(cursor.getFloat(cursor.getColumnIndex(KEY_WEIGHT)));
                android.util.Log.i("weight", "" + cursor.getColumnIndex(KEY_ABDOMEN));
                weight.setFat(cursor.getInt(cursor.getColumnIndex(KEY_FAT)));
                weight.setAbdomen(cursor.getFloat(4));
                weight.setWaist(cursor.getFloat(5));
                weight.setNote(cursor.getString(6));
                weight.setDate(cursor.getString(7));
                weight.setTime(cursor.getString(8));
                weight.setHips(cursor.getFloat(9));
                weightArrayList.add(weight);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return weightArrayList;

    }

    public ArrayList<Glucose> getAllGlucose(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_GLUCOSE + " where " + KEY_USER_ID + " = " + userId;

        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<Glucose> glucoseArrayList = new ArrayList<Glucose>();
        if (cursor.moveToFirst()) {
            do {
                Glucose glucose = new Glucose();
                glucose.setUserId(userId);
                glucose.setRowId(cursor.getInt(cursor.getColumnIndex(KEY_ROWID)));

                glucose.setGlucose(cursor.getFloat(cursor.getColumnIndex(KEY_GLUCOSE)));
                glucose.setHba1c(cursor.getFloat(cursor.getColumnIndex(KEY_HBA1C)));
                glucose.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
                glucose.setTime(cursor.getString(cursor.getColumnIndex(KEY_TIME)));
                glucose.setTestingTime(cursor.getString(cursor.getColumnIndex(KEY_TESTING_TIME)));
                glucose.setNote(cursor.getString(cursor.getColumnIndex(KEY_NOTE)));
                glucoseArrayList.add(glucose);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return glucoseArrayList;
    }

    public ArrayList<BloodPresure> getAllBP(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_B_P + " where " + KEY_USER_ID + " = " + userId;

        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<BloodPresure> bloodPresureArrayList = new ArrayList<BloodPresure>();
        if (cursor.moveToFirst()) {
            do {
                BloodPresure bloodPresure = new BloodPresure();
                bloodPresure.setUserId(userId);
                bloodPresure.setRowId(cursor.getInt(cursor.getColumnIndex(KEY_ROWID)));

                bloodPresure.setHeartrate(cursor.getInt(cursor.getColumnIndex(KEY_HEART_RATE)));
                bloodPresure.setSystolic(cursor.getInt(cursor.getColumnIndex(KEY_SYSTOLIC)));
                bloodPresure.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
                bloodPresure.setTime(cursor.getString(cursor.getColumnIndex(KEY_TIME)));
                bloodPresure.setDiastolic(cursor.getInt(cursor.getColumnIndex(KEY_DIASTOLIC)));
                bloodPresure.setNote(cursor.getString(cursor.getColumnIndex(KEY_NOTE)));
                bloodPresureArrayList.add(bloodPresure);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return bloodPresureArrayList;
    }

    public ArrayList<cholesterol> getAllCholestrol(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_CHOLESEROL + " where " + KEY_USER_ID + " = " + userId;

        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<cholesterol> cholesterolArrayList = new ArrayList<cholesterol>();
        if (cursor.moveToFirst()) {
            do {
                cholesterol cholesterol = new cholesterol();
                cholesterol.setUserId(userId);
                cholesterol.setRowId(cursor.getInt(cursor.getColumnIndex(KEY_ROWID)));

                cholesterol.setHdl(cursor.getInt(cursor.getColumnIndex(KEY_HDL)));
                cholesterol.setLdl(cursor.getInt(cursor.getColumnIndex(KEY_LDL)));
                cholesterol.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
                cholesterol.setTime(cursor.getString(cursor.getColumnIndex(KEY_TIME)));
                cholesterol.setTotal(cursor.getInt(cursor.getColumnIndex(KEY_TOTAL)));
                cholesterol.setNote(cursor.getString(cursor.getColumnIndex(KEY_NOTE)));
                cholesterol.setTriglyceride(cursor.getFloat(cursor.getColumnIndex(KEY_TRIGLYCERIDES)));
                cholesterolArrayList.add(cholesterol);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return cholesterolArrayList;
    }

    public ArrayList<Thyroid> getAllThyroid(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_THYROID + " where " + KEY_USER_ID + " = " + userId;

        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<Thyroid> bloodPresureArrayList = new ArrayList<Thyroid>();
        if (cursor.moveToFirst()) {
            do {
                Thyroid thyroid = new Thyroid();
                thyroid.setUserId(userId);
                thyroid.setRowId(cursor.getInt(cursor.getColumnIndex(KEY_ROWID)));

                thyroid.setTshLevel(cursor.getInt(cursor.getColumnIndex(KEY_TSH_LEVEL)));
                thyroid.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
                thyroid.setTime(cursor.getString(cursor.getColumnIndex(KEY_TIME)));
                thyroid.setNote(cursor.getString(cursor.getColumnIndex(KEY_NOTE)));
                bloodPresureArrayList.add(thyroid);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return bloodPresureArrayList;
    }
}
