package com.mksm.youcanapp.database.adaters;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mksm.youcanapp.database.DatabaseUtils;
import com.mksm.youcanapp.database.interfaces.TaskHandler;
import com.mksm.youcanapp.entities.Task;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by mskm on 24.01.2016.
 */
public class TaskDatabaseHandler implements TaskHandler{


    private static final String TABLE_NAME = "tasks";
    private static final String KEY_ID = "_id";
    private static final String KEY_TEXT = "text";
    private static final String KEY_DATE = "date";
    private static final String KEY_STATE = "state";

    private static Map<String, String> columns;

    static {
        columns = new HashMap<String, String>();
        columns.put(KEY_ID, "TEXT");
        columns.put(KEY_TEXT, "TEXT");
        columns.put(KEY_DATE, "TEXT");
        columns.put(KEY_STATE, "TEXT");
    }

    private DBAdapter mDbHelper;
    private SQLiteDatabase mDb;

    private final Context mCtx;

    public TaskDatabaseHandler(Context mCtx) {
        this.mCtx = mCtx;
    }

    public TaskDatabaseHandler open() throws SQLException {
        this.mDbHelper = new DBAdapter(this.mCtx);
        this.mDb = this.mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.mDbHelper.close();
    }

    @Override
    public Task getTaskById(String _id) {
        String[] args = {_id};
        Cursor cursor = mDb.query(true, TABLE_NAME, (String[]) columns.keySet().toArray(), "_id = ?", args, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst())
            {
                String text = cursor.getString(cursor.getColumnIndex(KEY_TEXT));
                Task.State state = Task.State.getById(cursor.getString(cursor.getColumnIndex(KEY_STATE)));
                Calendar date = DatabaseUtils.textToDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
                int id;
                id = Integer.parseInt(_id);
                if (cursor.isLast()) Log.d(DatabaseUtils.TAG, "getTaskById() has 1+ values");

                return new Task(id, text, date, state);

            }
        }
        return null;
    }

    @Override
    public List<Task> getTasksByDate(Calendar date) {
        String[] args = {DatabaseUtils.dateToText(date)};
        Cursor cursor = mDb.query(true, TABLE_NAME, (String[]) columns.keySet().toArray(), "date = ?", args, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst())
            {

                String text = cursor.getString(cursor.getColumnIndex(KEY_TEXT));
                Task.State state = Task.State.getById(cursor.getString(cursor.getColumnIndex(KEY_STATE)));
                Calendar date = DatabaseUtils.textToDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));

                return new Task(_id, text, date, state);

            }
        }
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

    @Override
    public Map<String, String> getColumns() {
        return null;
    }
}
