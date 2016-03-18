package com.mksm.youcanapp.presenters;

import com.mksm.youcanapp.database.DatabaseUtils;
import com.mksm.youcanapp.entities.implementations.Addiction;
import com.mksm.youcanapp.entities.implementations.Task;

import java.util.Calendar;

/**
 * Created by Техно on 29.02.2016.
 */


public class AddCheckablePresenter extends PresenterImpl {

    public void saveAddiction(String text, String dateString, String durationString) {
        Calendar date = DatabaseUtils.longToDate(Long.parseLong(dateString));
        int duration = Integer.parseInt(durationString);
        Addiction addiction = new Addiction(text, date, duration);
        addiction.save();
    }

    public void saveTask(String text, String dateString) {
        Calendar date = DatabaseUtils.longToDate(Long.parseLong(dateString));
        Task task = new Task(text,date);
        task.save();
    }


    @Override
    public void updateView() {
        return;
    }
}
