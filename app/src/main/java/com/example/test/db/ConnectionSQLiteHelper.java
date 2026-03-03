package com.example.test.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class ConnectionSQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "app.db";
    public static final int DATABASE_VERSION = 2;

    final String CREATE_TABLE_USER = "CREATE TABLE user(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, mobile TEXT, password TEXT, photo_path TEXT);";

    public ConnectionSQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE user ADD COLUMN photo_path TEXT");
        }
    }
}
