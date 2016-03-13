package com.mksm.youcanapp.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.Calendar;

/**
 * Created by mskm on 24.01.2016.
 */
public class DatabaseUtils {

    public static final String TAG = "DatabaseLog";
    public static final int WASNT_SAVE_IN_DB = -1000;

    /*
    YYYYMMDDHHMMSS - формат хранения числа.
     */
    public static long dateTimeToLong (Calendar date) {
        if (date == null) {
            return 0;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(date.get(Calendar.YEAR));
        sb.append(getDoubleNumberDate(date.get(Calendar.MONTH) + 1)); //0-based month
        sb.append(getDoubleNumberDate(date.get(Calendar.DAY_OF_MONTH)));
        sb.append(getDoubleNumberDate(date.get(Calendar.HOUR_OF_DAY)));
        sb.append(getDoubleNumberDate(date.get(Calendar.MINUTE)));
        sb.append(getDoubleNumberDate(date.get(Calendar.SECOND)));
        return Long.getLong(sb.toString());
    }

    public static Calendar longToDateTime (Long value) {
        if (value == null) {
            return null;
        }
        String text = String.valueOf(value);
        int year = Integer.parseInt(text.substring(0,4));
        int month = Integer.parseInt(text.substring(4, 6)) - 1; //0-based month
        int day = Integer.parseInt(text.substring(6, 8));
        int hour = Integer.parseInt(text.substring(8, 10));
        int minute = Integer.parseInt(text.substring(10, 12));
        int second = Integer.parseInt(text.substring(12, 14));

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, second);

        return calendar;
    }

    private static String getDoubleNumberDate (int date) {
        String dateString = String.valueOf(date);
        if (dateString.length() == 1) {
            return "0" + dateString;
        }
        else  return dateString;
    }

    public static long dateToLong (Calendar date) {
        if (date == null) {
            return 0;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(date.get(Calendar.YEAR));
        sb.append(getDoubleNumberDate(date.get(Calendar.MONTH) + 1)); //0-based month
        sb.append(getDoubleNumberDate(date.get(Calendar.DAY_OF_MONTH)));
        return Long.parseLong(sb.toString());
    }

    public static Calendar longToDate (long value) {
        String text = String.valueOf(value);
        int year = Integer.parseInt(text.substring(0,4));
        int month = Integer.parseInt(text.substring(4, 6)) - 1; //0-based month
        int day = Integer.parseInt(text.substring(6, 8));

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0, 0);

        return calendar;
    }
}
