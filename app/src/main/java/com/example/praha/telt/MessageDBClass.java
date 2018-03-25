package com.example.praha.telt;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.HashMap;


public class MessageDBClass extends SQLiteOpenHelper {

    SessionManager session;
    Context context;

    private static final String DBNAME = "Mydatabase";
    public static final String HISTORY = "his";
    public static final String NAME = "name";
    public static final String EXTRACT_TEXT = "extracted";
    public static final String TRANSLATE_TEXT = "translated";
    private static int DBVER = 1;
    SQLiteDatabase db1;

    public MessageDBClass(Context context) {
        super(context, DBNAME, null, DBVER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db1 = db;
        createTable();
    }

    public void createTable() {
        String createTable = "CREATE TABLE " + HISTORY + "("+ NAME + " TEXT, " + EXTRACT_TEXT + " TEXT, " + TRANSLATE_TEXT + " TEXT)";
        db1.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DBVER = newVersion;
    }

    public void insert(String name, String extract, String translate) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(EXTRACT_TEXT, extract);
        values.put(TRANSLATE_TEXT, translate);
        db.insert(HISTORY, null, values);
        db.close();
    }

    public Cursor getData() {
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + HISTORY ;
        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor;
    }
}
