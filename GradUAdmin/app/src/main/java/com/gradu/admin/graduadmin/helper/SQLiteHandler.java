package com.gradu.admin.graduadmin.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;
/**
 * Created by Pranshu on 11/25/2015.
 */

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "gradu_api";

    // Admin Login table name
    private static final String TABLE_ADMIN = "admin";

    // Login table name
    private static final String TABLE_USER = "user";

    // Admin Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_ADMIN_NAME = "admin_name";
    private static final String KEY_ADMIN_USERNAME = "admin_username";

    // Login Table Columns names
    private static final String KEY_NAME = "username";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ADMIN_LOGIN_TABLE = "CREATE TABLE " + TABLE_ADMIN + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ADMIN_NAME + " TEXT,"
                + KEY_ADMIN_USERNAME + " TEXT UNIQUE)";
        db.execSQL(CREATE_ADMIN_LOGIN_TABLE);

        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_NAME + " TEXT PRIMARY KEY)";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addAdmin(String admin_name, String admin_username) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ADMIN_NAME, admin_name); // Name
        values.put(KEY_ADMIN_USERNAME, admin_username); // Admin Username

        // Inserting Row
        long id = db.insert(TABLE_ADMIN, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New admin inserted into sqlite: " + id);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, username); // Username

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getAdminDetails() {
        HashMap<String, String> admin = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_ADMIN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            admin.put("admin_name", cursor.getString(1));
            admin.put("admin_username", cursor.getString(2));
        }
        cursor.close();
        db.close();
        // return admin
        Log.d(TAG, "Fetching user from Sqlite: " + admin.toString());

        return admin;
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "No. of Users Retrieved: " + cursor.getCount());
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("username", cursor.getString(0));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteAdmins() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_ADMIN, null, null);
        db.close();

        Log.d(TAG, "Deleted all admin info from sqlite");
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }
}