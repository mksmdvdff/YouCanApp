package com.mksm.youcanapp.database.interfaces;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.mksm.youcanapp.database.handlers.DatabaseAdapter;

import java.util.Map;

/**
 * Created by Техно on 13.03.2016.
 */
public abstract class DatabaseHandler implements DatabaseTable {

    public DatabaseHandler() {
        this.init();
    }

    protected DatabaseAdapter mDbHelper;
    protected SQLiteDatabase mDb;
    protected String TABLE_NAME;

    protected static Map<String, String> columns;

    protected void open() throws SQLException {
        this.mDbHelper = new DatabaseAdapter();
        this.mDb = this.mDbHelper.getWritableDatabase();
    }

    protected void close() {
        this.mDbHelper.close();
    }

    public Map<String, String> getColumns() {
        return columns;
    }

    public abstract void init();

    public String getTableName() {
        return TABLE_NAME;
    }
}
