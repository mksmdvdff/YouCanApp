package com.mksm.youcanapp.database.handlers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mksm.youcanapp.database.interfaces.DatabaseTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mskm on 26.01.2016.
 */
public class DatabaseAdapter extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "YouCanAppDB";
    static final int DATABASE_VERSION = 2;

    public DatabaseAdapter() {
        super(null, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        List<DatabaseTable> databaseTables = new ArrayList<>();
        databaseTables.add(TaskDatabaseHandler.getInstanceForDBCreating());
        databaseTables.addAll(AddictionDatabaseHandler.getInstanceForDBCreating());

        for (DatabaseTable databaseTable : databaseTables) {
            StringBuilder queryBuilder = new StringBuilder("CREATE TABLE ");
            queryBuilder.append(databaseTable.getTableName()).append(" (");
            for (Map.Entry<String, String> columns : databaseTable.getColumns().entrySet()) {
                queryBuilder.append(columns.getKey()).append(" ").append(columns.getValue()).append(",");
            }
            queryBuilder.deleteCharAt(queryBuilder.lastIndexOf(","));
            queryBuilder.append(")");
            db.execSQL(queryBuilder.toString());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        List<DatabaseTable> databaseTables = new ArrayList<>();
        databaseTables.add(TaskDatabaseHandler.getInstanceForDBCreating());
        databaseTables.addAll(AddictionDatabaseHandler.getInstanceForDBCreating());

        for (DatabaseTable databaseTable: databaseTables) {
            db.execSQL("DROP TABLE IF EXISTS " + databaseTable.getTableName() );
        }
        this.onCreate(db);
    }
}