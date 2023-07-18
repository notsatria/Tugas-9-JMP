package com.example.tugas_9;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    static  final String DATABASE_NAME = "catatanapp.db";

    public static final String TABLE_User_SQLite = "user";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    public static final String TABLE_Catatan_SQLite = "catatan";
    public static final String COLUMN_ID_CATATAN = "id";
    public static final String COLUMN_JUDUl_CATATAN = "judul";
    public static final String COLUMN_KONTEN_CATATAN = "konten";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE_USER = "CREATE TABLE " + TABLE_User_SQLite + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_USERNAME + " TEXT," +
                COLUMN_PASSWORD + " TEXT" +
                " )";

        final String SQL_CREATE_TABLE_CATATAN = "CREATE TABLE " + TABLE_Catatan_SQLite + " ( " +
                COLUMN_ID_CATATAN + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_JUDUl_CATATAN + " TEXT," +
                COLUMN_KONTEN_CATATAN + " TEXT" +
                " )";

        db.execSQL(SQL_CREATE_TABLE_USER);
        db.execSQL(SQL_CREATE_TABLE_CATATAN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_User_SQLite);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Catatan_SQLite);
        onCreate(db);
    }

    // Handle register
    public boolean registerUser(String name, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();


        String checkQuery = "SELECT * FROM " + TABLE_User_SQLite + " WHERE " + COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(checkQuery, new String[]{username});
        if (cursor.getCount() > 0) {
            // Username already exists, return or show an error message
            cursor.close();
            db.close();
            return false;
        }
        cursor.close();

        String query = "INSERT INTO " + TABLE_User_SQLite + " (name, username, password) " +
                "VALUES ( '" + name + "', '" + username + "', '" + password + "' )";


        Log.e("Query register", query);

        db.execSQL(query);
        db.close();

        return true;
    }

    public boolean loginUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_User_SQLite + " WHERE username = '" + username +
                "' AND password = '" + password + "'";
        Log.e("Query login", query);
        Cursor cursor = db.rawQuery(query, null);
        Log.e("Query count", cursor.getCount() + "");
        if (cursor.getCount() != 0) {
            result = true;
        }

        cursor.close();
        db.close();
        return result;
    }

    public boolean addCatatan(String judul, String konten) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        String query = "INSERT INTO " + TABLE_Catatan_SQLite + " (judul, konten) VALUES ('" +
                judul + "', '" + konten + "')";

        Log.e("Query Tambah Catatan", query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            result = true;
        }

        cursor.close();
        db.close();
        return result;
    }

    public boolean deleteCatatan(String judul, String konten) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        String query = "DELETE FROM " + TABLE_Catatan_SQLite + " WHERE " + "judul = " +
              judul + " AND  konten = " + konten;
        Log.e("Query Hapus Catatan", query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            result = true;
        }

        cursor.close();
        db.close();
        return result;
    }

    public List<String[]> getCatatanList() {
        List<String[]> listItemCatatan = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_JUDUl_CATATAN + ", " + COLUMN_KONTEN_CATATAN +
                " FROM " + TABLE_Catatan_SQLite;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int titleIndex = cursor.getColumnIndex(COLUMN_JUDUl_CATATAN);
                int contentIndex = cursor.getColumnIndex(COLUMN_KONTEN_CATATAN);

                String title = cursor.getString(titleIndex);
                String content = cursor.getString(contentIndex);

                String[] item = {title, content};
                listItemCatatan.add(item);
            } while (cursor.moveToNext());
        }

        if(cursor != null) {
            cursor.close();
        }

        db.close();

        return listItemCatatan;
    }
}
