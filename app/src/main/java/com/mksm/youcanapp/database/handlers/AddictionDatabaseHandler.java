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
import com.mksm.youcanapp.entities.Addiction;
import com.mksm.youcanapp.entities.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mskm on 31.01.2016.
 */
public class AddictionDatabaseHandler implements AddictionHandler {
    private static final String TABLE_NAME = "addictions";

    private static final String KEY_ID = "_id";
    private static final String KEY_TEXT = "text";
    private static final String KEY_START_DATE = "start_date";
    private static final String KEY_END_DATE = "end_date";
    private static final String KEY_DURATION = "duration";

    private static Map<String, String> columns;

    static {
        columns = new HashMap<String, String>();
        columns.put(KEY_ID, "INTEGER PRIMARY KEY");
        columns.put(KEY_TEXT, "TEXT");
        columns.put(KEY_START_DATE, "INTEGER");
        columns.put(KEY_END_DATE, "INTEGER");
        columns.put(KEY_DURATION, "INTEGER");
    }

    private DBAdapter mDbHelper;
    private SQLiteDatabase mDb;

    private final Context mCtx;

    public AddictionDatabaseHandler(Context mCtx) {
        this.mCtx = mCtx;
    }

    public AddictionDatabaseHandler open() throws SQLException {
        this.mDbHelper = new DBAdapter(this.mCtx);
        this.mDb = this.mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.mDbHelper.close();
    }

    @Override
    public Addiction getAddictionById(long _id) {


        String[] args = {String.valueOf(_id)};
        Cursor cursor = mDb.query(true, TABLE_NAME, (String[]) columns.keySet().toArray(), "_id = ?", args, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst())
            {
                String text = cursor.getString(cursor.getColumnIndex(KEY_TEXT));
                Calendar startDate = DatabaseUtils.longToDate(cursor.getLong(cursor.getColumnIndex(KEY_START_DATE)));
                Calendar endDate = DatabaseUtils.longToDate(cursor.getLong(cursor.getColumnIndex(KEY_END_DATE)));
                int duration = cursor.getInt(cursor.getColumnIndex(KEY_DURATION));

                if (!cursor.isLast()) Log.d(DatabaseUtils.TAG, "getTaskById() has 1+ values");

                AddictionDatesDatabaseHandler adh = new AddictionDatesDatabaseHandler(this.mCtx);
                adh.open();
                List<Calendar> dates = adh.getDates(_id);

                return new Addiction(_id, text, startDate, endDate, duration, dates);

            }
        }
        return null;
    }

    @Override
    public List<Addiction> getAddictionsByDate(Calendar date) {
        if (date == null)
            return null;

        List<Addiction> result = new ArrayList<Addiction>();
        String[] args = {String.valueOf(DatabaseUtils.dateToLong(date)), String.valueOf(DatabaseUtils.dateToLong(date))};
        Cursor cursor = mDb.query(true, TABLE_NAME, (String[]) columns.keySet().toArray(), "start_date < ? AND end_date > ?", args, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String text = cursor.getString(cursor.getColumnIndex(KEY_TEXT));
                    Calendar startDate = DatabaseUtils.longToDate(cursor.getLong(cursor.getColumnIndex(KEY_START_DATE)));
                    Calendar endDate = DatabaseUtils.longToDate(cursor.getLong(cursor.getColumnIndex(KEY_END_DATE)));
                    int duration = cursor.getInt(cursor.getColumnIndex(KEY_DURATION));
                    long id = cursor.getLong(cursor.getColumnIndex(KEY_ID));

                    AddictionDatesDatabaseHandler adh = new AddictionDatesDatabaseHandler(this.mCtx);
                    adh.open();
                    List<Calendar> dates = adh.getDates(id);

                    result.add(new Addiction(id,text,startDate,endDate,duration,dates));
                }
                while (cursor.moveToNext());

            }
        }
        return result;
    }

    @Override
    public void addNewAddiction(Addiction addiction) {
        if (addiction==null)
            return;

        ContentValues content = new ContentValues();
        content.put(KEY_TEXT, task.getText());
        content.put(KEY_STATE, task.getState().getId());
        content.put(KEY_DATE, DatabaseUtils.dateToLong(task.getDate()));
        long id = mDb.insert(TABLE_NAME, null, content);



    }

    @Override
    public void addNewAddictions(List<Addiction> addictions) {
        if (tasks==null || tasks.isEmpty())
            return;

        for (Task task : tasks) {
            ContentValues content = new ContentValues();
            content.put(KEY_TEXT, task.getText());
            content.put(KEY_STATE, task.getState().getId());
            content.put(KEY_DATE, DatabaseUtils.dateToLong(task.getDate()));
            mDb.insert(TABLE_NAME, null, content);
        }
    }

    @Override
    public void updateAddiction(Addiction addiction) {

        ContentValues content = new ContentValues();
        content.put(KEY_TEXT, task.getText());
        content.put(KEY_STATE, task.getState().getId());
        content.put(KEY_DATE, DatabaseUtils.dateToLong(task.getDate()));
        mDb.update(TABLE_NAME,content,"_id = ?", new String[]{String.valueOf(task.getId())});
    }

    @Override
    public void addNewDate(Addiction addiction, Calendar date) {

    }

    @Override
    public void deleteAddiction(Addiction addiction) {
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

    private static class AddictionDatesDatabaseHandler implements AddictionDatesHandler {
        private static final String TABLE_NAME = "addictionDates";

        private static final String KEY_ID = "_id";
        private static final String KEY_DATE = "date";

        private static Map<String, String> columns;

        private DBAdapter mDbHelper;
        private SQLiteDatabase mDb;

        private final Context mCtx;

        static {
            columns = new HashMap<String, String>();
            columns.put(KEY_ID, "INTEGER");
            columns.put(KEY_DATE, "INTEGER");
        }

        public AddictionDatesDatabaseHandler(Context mCtx) {
            this.mCtx = mCtx;
        }

        public AddictionDatesHandler open() throws SQLException {
            this.mDbHelper = new DBAdapter(this.mCtx);
            this.mDb = this.mDbHelper.getWritableDatabase();
            return this;
        }

        public void close() {
            this.mDbHelper.close();
        }

        @Override
        public List<Calendar> getDates(long id) {
            String[] args = {String.valueOf(id)};
            Cursor cursor = mDb.query(true, TABLE_NAME, (String[]) columns.keySet().toArray(), "_id = ?", args, null, null, null, null);
            List<Calendar> result = new ArrayList<Calendar>();
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

        @Override
        public void addNewDate(long id, Calendar date) {
            ContentValues content = new ContentValues();
            content.put(KEY_ID, id);
            content.put(KEY_DATE, DatabaseUtils.dateToLong(date));
            mDb.insert(TABLE_NAME,null,content);
        }

        @Override
        public Map<String, String> getColumns() {
            return columns;
        }

        @Override
        public String getTableName() {
            return TABLE_NAME;
        }
    }
}
