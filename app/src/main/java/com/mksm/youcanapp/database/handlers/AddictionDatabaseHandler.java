package com.mksm.youcanapp.database.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mksm.youcanapp.database.DatabaseUtils;
import com.mksm.youcanapp.database.interfaces.AddictionDatesHandler;
import com.mksm.youcanapp.database.interfaces.AddictionHandler;
import com.mksm.youcanapp.database.interfaces.DatabaseHandler;
import com.mksm.youcanapp.database.interfaces.DatabaseTable;
import com.mksm.youcanapp.entities.implementations.Addiction;
import com.mksm.youcanapp.entities.interfaces.Entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mskm on 31.01.2016.
 */
public class AddictionDatabaseHandler extends DatabaseHandler implements AddictionHandler {

    private static final String KEY_ID = "_id";
    private static final String KEY_TEXT = "text";
    private static final String KEY_START_DATE = "start_date";
    private static final String KEY_END_DATE = "end_date";
    private static final String KEY_DURATION = "duration";

    public void init() {
        columns = new HashMap<>();
        columns.put(KEY_ID, "INTEGER PRIMARY KEY");
        columns.put(KEY_TEXT, "TEXT");
        columns.put(KEY_START_DATE, "INTEGER");
        columns.put(KEY_END_DATE, "INTEGER");
        columns.put(KEY_DURATION, "INTEGER");
    }

    @Override
    public Entity getEntity(long id) {

        open();
        try {
            String[] args = {String.valueOf(id)};
            Cursor cursor = mDb.query(true, TABLE_NAME, (String[]) columns.keySet().toArray(), "_id = ?", args, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    String text = cursor.getString(cursor.getColumnIndex(KEY_TEXT));
                    Calendar startDate = DatabaseUtils.longToDate(cursor.getLong(cursor.getColumnIndex(KEY_START_DATE)));
                    Calendar endDate = DatabaseUtils.longToDate(cursor.getLong(cursor.getColumnIndex(KEY_END_DATE)));
                    int duration = cursor.getInt(cursor.getColumnIndex(KEY_DURATION));

                    if (!cursor.isLast()) Log.d(DatabaseUtils.TAG, "getTaskById() has 1+ values");

                    AddictionDatesDatabaseHandler adh = new AddictionDatesDatabaseHandler();
                    List<Calendar> dates = adh.getDates(String.valueOf(id));

                    return new Addiction(id, text, startDate, endDate, duration, dates);

                }
            }
            return null;
        } finally {
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
            String[] args = {String.valueOf(DatabaseUtils.dateToLong(date)), String.valueOf(DatabaseUtils.dateToLong(date))};
            Cursor cursor = mDb.query(true, TABLE_NAME, columns.keySet().toArray(new String[columns.keySet().size()]),
                    "start_date < ? AND end_date > ?", args, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        String text = cursor.getString(cursor.getColumnIndex(KEY_TEXT));
                        Calendar startDate = DatabaseUtils.longToDate(cursor.getLong(cursor.getColumnIndex(KEY_START_DATE)));
                        Calendar endDate = DatabaseUtils.longToDate(cursor.getLong(cursor.getColumnIndex(KEY_END_DATE)));
                        int duration = cursor.getInt(cursor.getColumnIndex(KEY_DURATION));
                        long id = cursor.getLong(cursor.getColumnIndex(KEY_ID));

                        AddictionDatesDatabaseHandler adh = new AddictionDatesDatabaseHandler();
                        List<Calendar> dates = adh.getDates(String.valueOf(id));

                        result.add(new Addiction(id, text, startDate, endDate, duration, dates));
                    }
                    while (cursor.moveToNext());

                }
            }
            return result;
        } finally {
            close();
        }
    }

    @Override
    public void save(Entity entity) {
        if (entity == null) {
            return;
        }
        Addiction addiction = (Addiction) entity;
        open();
        try {
            ContentValues content = new ContentValues();
            content.put(KEY_TEXT, addiction.getText());
            content.put(KEY_START_DATE, DatabaseUtils.dateToLong(addiction.getStartDate()));
            content.put(KEY_END_DATE, DatabaseUtils.dateToLong(addiction.getEndDate()));
            content.put(KEY_DURATION, addiction.getDuration());
            if (addiction.getId() == DatabaseUtils.WASNT_SAVE_IN_DB) {
                long id = mDb.insert(TABLE_NAME, null, content);
                addiction.setId(id);
            } else {
                mDb.update(TABLE_NAME, content, "_id = ?", new String[]{String.valueOf(addiction.getId())});
            }
        } finally {
            close();
        }
    }

    @Override
    public void addNewDates(Addiction addiction, List<Calendar> dates) {
        AddictionDatesDatabaseHandler adh = new AddictionDatesDatabaseHandler();
        adh.addDates(addiction.getId(), dates);
    }

    @Override
    public void delete(Entity entity) {
        if (entity == null)
            return;
        open();
        Addiction addiction = (Addiction) entity;
        try {
            mDb.delete(TABLE_NAME, "_id = ?", new String[]{String.valueOf(addiction.getId())});
        } finally {
            close();
        }
    }

     public static class AddictionDatesDatabaseHandler extends DatabaseHandler implements DatabaseTable, AddictionDatesHandler  {

         private static final String KEY_ID = "_id";
         private static final String KEY_DATE = "date";

         @Override
         public void init() {
             columns = new HashMap<>();
             columns.put(KEY_ID, "INTEGER");
             columns.put(KEY_DATE, "INTEGER");

             this.TABLE_NAME = "addictionDates";
         }


        public List<Calendar> getDates(String id) {
            open();
            try {
                String[] args = {id};
                Cursor cursor = mDb.query(true, TABLE_NAME, columns.keySet().toArray(new String[columns.keySet().size()]),
                        "_id = ?", args, null, null, null, null);
                List<Calendar> result = new ArrayList<>();
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        do {
                            Calendar date = DatabaseUtils.longToDate(cursor.getInt(cursor.getColumnIndex(KEY_DATE)));

                            result.add(date);
                        }
                        while (cursor.moveToNext());

                    }
                }
                return result;
            }
            finally {
                close();
            }
        }

        public void addDates(long id, List<Calendar> dates) {
            open();
            try {
                for (Calendar date : dates) {
                    ContentValues content = new ContentValues();
                    content.put(KEY_ID, id);
                    content.put(KEY_DATE, DatabaseUtils.dateToLong(date));
                    mDb.insertWithOnConflict(TABLE_NAME, null, content, SQLiteDatabase.CONFLICT_REPLACE);
                }
            }
            finally {
                close();
            }
        }
    }
}
