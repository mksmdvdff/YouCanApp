package com.mksm.youcanapp.database.handlers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mksm.youcanapp.database.interfaces.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mskm on 26.01.2016.
 */
public class DatabaseAdapter extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "YouCanAppDB";
    static final int DATABASE_VERSION = 1;

    public DatabaseAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        List<Handler> handlers = new ArrayList<Handler>();
        handlers.add(TaskDatabaseHandler.getInstanceForDBCreating());
        handlers.addAll(AddictionDatabaseHandler.getInstanceForDBCreating());

        for (Handler handler : handlers) {
            StringBuilder queryBuilder = new StringBuilder("CREATE TABLE ");
            queryBuilder.append(handler.getTableName()).append(" (");
            for (Map.Entry<String, String> columns : handler.getColumns().entrySet()) {
                queryBuilder.append(columns.getKey()).append(" ").append(columns.getValue()).append(",");
            }
            queryBuilder.deleteCharAt(queryBuilder.lastIndexOf(","));
            queryBuilder.append(")");
            System.out.println(queryBuilder.toString());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}