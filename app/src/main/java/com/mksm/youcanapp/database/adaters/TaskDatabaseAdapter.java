package com.mksm.youcanapp.database.adaters;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mksm.youcanapp.database.interfaces.TaskHandler;
import com.mksm.youcanapp.entities.Task;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by mskm on 24.01.2016.
 */
public class TaskDatabaseAdapter implements TaskHandler{


    private static final String TABLE_NAME = "tasks";
    private static final String KEY_ID = "_id";
    private static final String KEY_TEXT = "text";
    private static final String KEY_DATE = "date";
    private static final String KEY_STATE = "state";

    private static String[] columns = {KEY_ID, KEY_TEXT, KEY_DATE, KEY_STATE};

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private final Context mCtx;

    public TaskDatabaseAdapter(Context mCtx) {
        this.mCtx = mCtx;
    }

    public TaskDatabaseAdapter open() throws SQLException {
        this.mDbHelper = new DatabaseHelper(this.mCtx);
        this.mDb = this.mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.mDbHelper.close();
    }

    @Override
    public Task getTaskById(String _id) {
        String[] args = {_id};
        Cursor cursor = mDb.query(true, TABLE_NAME, columns, "_id = ?", args, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst())
            {

            }
        }
        return null;
    }

    @Override
    public List<Task> getTasksByDate(Calendar date) {
        return null;
    }

    @Override
    public void addNewTask(Task task) {

    }

    @Override
    public void addNewTasks(List<Task> tasks) {

    }

    @Override
    public void updateTask(Task task) {

    }

    @Override
    public void deleteTask(Task task) {

    }

    @Override
    public void deleteAll() {

    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DBAdapter.DATABASE_NAME, null, DBAdapter.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
