package com.example.weighttrackerappcodyphelps;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private final Context context;
    private static final String DATABASE_NAME = "Weight_Tracker.db";
    private static final int DATABASE_VERSION = 1;
    // table for login info
    public static final String TABLE_USER_LOGIN = "users";
    public static final String COLUMN_USER_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    // USER_INFO table
    public static final String TABLE_USER_INFO = "user_info";
    public static final String COLUMN_INFO_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_CURRENT_WEIGHT = "current_weight";
    public static final String COLUMN_PREVIOUS_WEIGHT = "previous_weight";
    public static final String COLUMN_GOAL_WEIGHT = "goal_weight";

    // Create user-login table
    private static final String CREATE_USER_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER_LOGIN + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USERNAME + " TEXT UNIQUE, "
            + COLUMN_PASSWORD +" TEXT);";

    private static final String CREATE_USER_INFO_TABLE = "CREATE TABLE " + TABLE_USER_INFO + "("
            + COLUMN_INFO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_AGE + " INTEGER, "
            + COLUMN_DATE + " TEXT, "
            + COLUMN_CURRENT_WEIGHT + " INTEGER,"
            + COLUMN_PREVIOUS_WEIGHT + " INTEGER, "
            + COLUMN_GOAL_WEIGHT + " INTEGER);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_LOGIN_TABLE);
        db.execSQL(CREATE_USER_INFO_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_INFO);
        onCreate(db);

    }

    public long addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USER_LOGIN, null, values);
        if(result == -1) {
            Toast.makeText(context, "Failed to create username/password", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Account Created", Toast.LENGTH_SHORT).show();
        }
        db.close();
        return result;
    }

    public long addUserInfo(String name,Integer currAge, Integer currWeight ,String currDate, Integer goalWeight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues userValue = new ContentValues();
        userValue.put(COLUMN_NAME, name);
        userValue.put(COLUMN_AGE,currAge);
        userValue.put(COLUMN_CURRENT_WEIGHT, currWeight);
        userValue.put(COLUMN_DATE, currDate);
        userValue.put(COLUMN_PREVIOUS_WEIGHT, 0);
        userValue.put(COLUMN_GOAL_WEIGHT, goalWeight);
        long userResult = db.insert(TABLE_USER_INFO, null, userValue);
        if(userResult == -1) {
            Toast.makeText(context, "Failed to add Name or Weight", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Account Created", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "addUserInfo: Created");
        }
        db.close();
        return userResult;
    }

    public boolean usernameExist(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USER_LOGIN + " WHERE " + COLUMN_USERNAME
                + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public boolean checkUser(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USER_LOGIN + " WHERE "
                + COLUMN_USERNAME + " = ? AND "
                + COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username,password});
        boolean match = cursor.moveToFirst();
        cursor.close();
        return match;
    }

    public Cursor readWeightData() {
        String query = "SELECT " + COLUMN_INFO_ID + ", " + COLUMN_CURRENT_WEIGHT + ", " + COLUMN_PREVIOUS_WEIGHT
                + ", " + COLUMN_DATE + ", " + COLUMN_GOAL_WEIGHT + " FROM " + TABLE_USER_INFO;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public String getUserName(){
        String query = "SELECT " + COLUMN_NAME + " FROM " + TABLE_USER_INFO;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        String userName = null;

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_NAME);
            if (columnIndex != -1) {
                userName = cursor.getString(columnIndex);
            }
        }

        cursor.close();
        db.close();

        return userName;
    }

    public int getUserAge(){
        String query = "SELECT " + COLUMN_AGE + " FROM " + TABLE_USER_INFO;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        int userAge = 0; // Default age if no data is retrieved

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_AGE);
            if (columnIndex != -1) {
                userAge = cursor.getInt(columnIndex);
            }
        }

        cursor.close();
        db.close();

        return userAge;
    }

    public int getUserGoal(){
        String query = "SELECT " + COLUMN_GOAL_WEIGHT + " FROM " + TABLE_USER_INFO;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        int userGoal = 0; // Default age if no data is retrieved

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_GOAL_WEIGHT);
            if (columnIndex != -1) {
                userGoal = cursor.getInt(columnIndex);
            }
        }

        cursor.close();
        db.close();

        return userGoal;
    }

    public int getCurrentWeight(){
        String query = "SELECT " + COLUMN_CURRENT_WEIGHT + " FROM " + TABLE_USER_INFO + " ORDER BY "
                + COLUMN_INFO_ID + " DESC LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        int userCurrentWeight = 0; // Default age if no data is retrieved

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_CURRENT_WEIGHT);
            if (columnIndex != -1) {
                userCurrentWeight = cursor.getInt(columnIndex);
            }
        }

        cursor.close();
        db.close();

        return userCurrentWeight;
    }


    public long updateUserInfo(String name, int age, int currWeight, String currDate, int goal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues userValue = new ContentValues();

        // Get the previous weight from the last record in the database
        String prevWeight = "0";
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_CURRENT_WEIGHT + " FROM " + TABLE_USER_INFO + " ORDER BY " + COLUMN_INFO_ID + " DESC LIMIT 1", null);

        if (cursor != null && cursor.moveToFirst()) {
            int prevWeightIndex = cursor.getColumnIndex(COLUMN_CURRENT_WEIGHT);
            if (prevWeightIndex != -1) {
                prevWeight = cursor.getString(prevWeightIndex);
            }
            cursor.close();
        }

        userValue.put(COLUMN_NAME, name);
        userValue.put(COLUMN_AGE, age);
        userValue.put(COLUMN_CURRENT_WEIGHT, currWeight);
        userValue.put(COLUMN_DATE, currDate);
        userValue.put(COLUMN_PREVIOUS_WEIGHT, prevWeight); // Update previous weight
        userValue.put(COLUMN_GOAL_WEIGHT, goal);

        long userResult = db.insert(TABLE_USER_INFO, null, userValue);
        Log.d(TAG, "Insert result: " + userResult);

        if(userResult == -1) {
            Toast.makeText(context, "Failed to add Name or Weight", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Weight added successfully", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "updateUserInfo: Added");
        }
        db.close();
        return userResult;
    }

    public long deleteItem(int position) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Get the item ID from the position
        int itemId = position;
        if (position == 1) {
            return -1;
        }

        Log.d("DeleteItem", "Deleting item with ID: " + itemId);

        long result = db.delete(TABLE_USER_INFO, COLUMN_INFO_ID + " = ?",
                new String[]{String.valueOf(itemId)});

        db.close();
        return result;
    }

    public void resetIncrement(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ=1 WHERE NAME='" + tableName + "'");
        Log.d(TAG,"Reset Increment for" + tableName);
        db.close();
    }

    public int getCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_USER_INFO, null);
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        db.close();
        return count;
    }





}
