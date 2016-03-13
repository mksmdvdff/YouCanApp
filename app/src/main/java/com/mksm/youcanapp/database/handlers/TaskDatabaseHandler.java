package com.mksm.youcanapp.database.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mksm.youcanapp.database.DatabaseUtils;
import com.mksm.youcanapp.database.interfaces.DatabaseHandler;
import com.mksm.youcanapp.database.interfaces.Handler;
import com.mksm.youcanapp.database.interfaces.TaskHandler;
import com.mksm.youcanapp.entities.implementations.Task;
import com.mksm.youcanapp.entities.interfaces.Entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mskm on 24.01.2016.
 */
public class TaskDatabaseHandler extends DatabaseHandler implements TaskHandler{

    private static final String KEY_ID = "_id";
    private static final String KEY_TEXT = "text";
    private static final String KEY_DATE = "date";
    private static final String KEY_STATE = "state";

    @Override
    public Entity getEntity(long id) {
        open();
        try {
            String[] args = {String.valueOf(id)};
            Cursor cursor = mDb.query(true, TABLE_NAME, columns.keySet().toArray(new String[columns.keySet().size()]),
                    "_id = ?", args, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    String text = cursor.getString(cursor.getColumnIndex(KEY_TEXT));
                    Task.State state = Task.State.getById(cursor.getString(cursor.getColumnIndex(KEY_STATE)));
                    Calendar date = DatabaseUtils.longToDate(cursor.getLong(cursor.getColumnIndex(KEY_DATE)));
                    if (!cursor.isLast()) Log.d(DatabaseUtils.TAG, "getTaskById() has 1+ values");

                    return new Task(id, text, date, state);

                }
            }
            return null;
        }
        finally {
            close();
        }
    }

    @Override
    public List<Entity> getEntitiesByDate(Calendar date) {
        if (date == null)
            return null;
        open();
        try {
            List<Entity> result = new ArrayList<>();
            String[] args = {String.valueOf(DatabaseUtils.dateToLong(date))};
            Cursor cursor = mDb.query(true, TABLE_NAME, columns.keySet().toArray(new String[columns.keySet().size()]),
                    "date = ?", args, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        String text = cursor.getString(cursor.getColumnIndex(KEY_TEXT));
                        Task.State state = Task.State.getById(cursor.getString(cursor.getColumnIndex(KEY_STATE)));
                        int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                        result.add(new Task(id, text, date, state));
                    }
                    while (cursor.moveToNext());

                }
            }
            return result;
        }
        catch (Exception ex) {
            throw new RuntimeException("", ex);
        }
        finally {
            close();
        }
    }

    @Override
    public void save(Entity entity) {
        if (entity==null) {
            return;
        }
        Task task = (Task) entity;
        open();
        try {
            ContentValues content = new ContentValues();
            content.put(KEY_TEXT, task.getText());
            content.put(KEY_STATE, task.getState().getId());
            content.put(KEY_DATE, DatabaseUtils.dateToLong(task.getDate()));
            if (task.getId() == DatabaseUtils.WASNT_SAVE_IN_DB) {
                long id = mDb.insert(TABLE_NAME, null, content);
                task.setId(id);
            }
            else {
                mDb.update(TABLE_NAME, content, "_id = ?", new String[]{String.valueOf(task.getId())});
            }
        }
        finally {
            close();
        }
    }

    @Override
    public void delete(Entity entity) {
        if (entity == null)
            return;

        Task task = (Task) entity;
        if (task.getId() == DatabaseUtils.WASNT_SAVE_IN_DB) {
            return;
        }
        else {
            open();
            try {
                mDb.delete(TABLE_NAME, "_id = ?", new String[]{String.valueOf(task.getId())});
            } finally {
                close();
            }
        }
    }

    @Override
    public void init() {
        columns = new HashMap<>();
        columns.put(KEY_ID, "INTEGER PRIMARY KEY");
        columns.put(KEY_TEXT, "TEXT");
        columns.put(KEY_DATE, "INTEGER");
        columns.put(KEY_STATE, "TEXT");

        this.TABLE_NAME = "tasks";
    }
}
