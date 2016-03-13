package com.mksm.youcanapp.presenters;

import com.mksm.youcanapp.database.DatabaseUtils;
import com.mksm.youcanapp.entities.implementations.Addiction;

import java.util.Calendar;

import roboguice.inject.ContextSingleton;

/**
 * Created by Техно on 29.02.2016.
 */

@ContextSingleton
public class AddCheckablePresenter {

    public void saveAddiction(String text, String dateString, String durationString) {
        Calendar date = DatabaseUtils.longToDate(Long.parseLong(dateString));
        int duration = Integer.parseInt(durationString);
        Addiction addiction = new Addiction(text, date, duration);
        addiction.save();
    }

    public void saveTask(String text, String date) {

    }
}
