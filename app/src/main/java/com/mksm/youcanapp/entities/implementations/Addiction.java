package com.mksm.youcanapp.entities.implementations;

import com.mksm.youcanapp.database.DatabaseUtils;
import com.mksm.youcanapp.database.handlers.AddictionDatabaseHandler;
import com.mksm.youcanapp.database.interfaces.Handler;
import com.mksm.youcanapp.entities.interfaces.Checkable;
import com.mksm.youcanapp.session.YouCanSession;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by mskm on 31.01.2016.
 */
public class Addiction implements Checkable {

    long id;
    String text;
    Calendar startDate;
    Calendar endDate;
    int duration;
    List<Calendar> doneDates;
    Handler handler;

    public Addiction(long id, String text, Calendar startDate, Calendar endDate, int duration, List<Calendar> doneDates) {
        this.id = id;
        this.text = text;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.doneDates = doneDates;
        this.handler = new AddictionDatabaseHandler();
    }

    public Addiction(String text, Calendar startDate, int duration) {
        this.id = DatabaseUtils.WASNT_SAVE_IN_DB; //до сохранения в реляционке сделаем заглушку.
        this.text = text;
        this.startDate = startDate;
        this.duration = duration;
        this.endDate = getEndDate(startDate, duration);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean isChecked() {
        return getDoneDates().contains(YouCanSession.get().getDate());
    }

    @Override
    public Handler getHandler() {
        return null;
    }

    @Override
    public void save() {
        handler.save(this);
    }

    @Override
    public void delete() {
        handler.delete(this);
    }

    public void setText(String text) {
        this.text = text;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Calendar> getDoneDates() {
        if (this.doneDates == null)
            this.doneDates = new ArrayList<Calendar>();
        return doneDates;
    }

    private Calendar getEndDate (Calendar startDate, int duration) {
        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.DAY_OF_MONTH, duration);
        return endDate;
    }

    @Override
    public void markChecked() {
        this.getDoneDates().add(YouCanSession.get().getDate());
    }

    @Override
    public void markUnchecked() {
        this.getDoneDates().remove(YouCanSession.get().getDate());
    }
}
