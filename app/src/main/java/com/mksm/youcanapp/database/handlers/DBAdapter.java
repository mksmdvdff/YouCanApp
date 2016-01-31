package com.mksm.youcanapp.database.handlers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mskm on 26.01.2016.
 */
public class DBAdapter extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "YouCanAppDB";
    static final int DATABASE_VERSION = 1;

    public DBAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
