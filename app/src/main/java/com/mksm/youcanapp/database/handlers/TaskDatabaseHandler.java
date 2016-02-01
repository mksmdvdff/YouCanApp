package com.mksm.youcanapp.database.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mksm.youcanapp.database.DatabaseUtils;
import com.mksm.youcanapp.database.interfaces.Handler;
import com.mksm.youcanapp.database.interfaces.TaskHandler;
import com.mksm.youcanapp.entities.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        columns.put(KEY_ID, "INTEGER PRIMARY KEY");
        columns.put(KEY_TEXT, "TEXT");
        columns.put(KEY_DATE, "INTEGER");
        columns.put(KEY_STATE, "TEXT");
    }

    private DatabaseAdapter mDbHelper;
    private SQLiteDatabase mDb;

    private final Context mCtx;

    public TaskDatabaseHandler(Context mCtx) {
        this.mCtx = mCtx;
    }

    /*
    For DB creating o
     */
    private TaskDatabaseHandler() {
        this.mCtx = null;
    }

    public TaskDatabaseHandler open() throws SQLException {
        this.mDbHelper = new DatabaseAdapter(this.mCtx);
        this.mDb = this.mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.mDbHelper.close();
    }

    @Override
    public Task getTaskById(long _id) {


        String[] args = {String.valueOf(_id)};
        Cursor cursor = mDb.query(true, TABLE_NAME, (String[]) columns.keySet().toArray(), "_id = ?", args, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst())
            {
                String text = cursor.getString(cursor.getColumnIndex(KEY_TEXT));
                Task.State state = Task.State.getById(cursor.getString(cursor.getColumnIndex(KEY_STATE)));
                Calendar date = DatabaseUtils.longToDate(cursor.getLong(cursor.getColumnIndex(KEY_DATE)));
                if (!cursor.isLast()) Log.d(DatabaseUtils.TAG, "getTaskById() has 1+ values");

                return new Task(_id, text, date, state);

            }
        }
        return null;
    }

    @Override
    public List<Task> getTasksByDate(Calendar date) {
        if (date == null)
            return null;

        List<Task> result = new ArrayList<Task>();
        String[] args = {String.valueOf(DatabaseUtils.dateToLong(date))};
        Cursor cursor = mDb.query(true, TABLE_NAME, (String[]) columns.keySet().toArray(), "date = ?", args, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String text = cursor.getString(cursor.getColumnIndex(KEY_TEXT));
                    Task.State state = Task.State.getById(cursor.getString(cursor.getColumnIndex(KEY_STATE)));
                    int id =cursor.getInt(cursor.getColumnIndex(KEY_ID));
                result.add(new Task(id, text, date, state));
                }
                while (cursor.moveToNext());

            }
        }
        return result;
    }

    @Override
    public void addNewTask(Task task) {
        if (task==null)
            return;

        ContentValues content = new ContentValues();
        content.put(KEY_TEXT, task.getText());
        content.put(KEY_STATE, task.getState().getId());
        content.put(KEY_DATE, DatabaseUtils.dateToLong(task.getDate()));
        long id = mDb.insert(TABLE_NAME, null, content);
        task.setId(id);

    }

    @Override
    public void addNewTasks(List<Task> tasks) {
        if (tasks==null || tasks.isEmpty())
            return;

        for (Task task : tasks) {
            ContentValues content = new ContentValues();
            content.put(KEY_TEXT, task.getText());
            content.put(KEY_STATE, task.getState().getId());
            content.put(KEY_DATE, DatabaseUtils.dateToLong(task.getDate()));
            long id = mDb.insert(TABLE_NAME, null, content);
            task.setId(id);
        }
    }

    @Override
    public void updateTask(Task task) {

        ContentValues content = new ContentValues();
        content.put(KEY_TEXT, task.getText());
        content.put(KEY_STATE, task.getState().getId());
        content.put(KEY_DATE, DatabaseUtils.dateToLong(task.getDate()));
        mDb.update(TABLE_NAME,content,"_id = ?", new String[]{String.valueOf(task.getId())});
    }

    @Override
    public void deleteTask(Task task) {
        if (task == null)
            return;
        mDb.delete(TABLE_NAME,"_id = ?", new String[]{String.valueOf(task.getId())});
    }

    @Override
    public void deleteAll() {
        mDb.delete(TABLE_NAME, null, null);
    }

    @Override
    public Map<String, String> getColumns() {
        return this.columns;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static Handler getInstanceForDBCreating() {
        return new TaskDatabaseHandler();
    }
}
