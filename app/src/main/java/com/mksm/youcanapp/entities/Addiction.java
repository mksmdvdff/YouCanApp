package com.mksm.youcanapp.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by mskm on 31.01.2016.
 */
public class Addiction {

    long id;
    String text;
    Calendar startDate;
    Calendar endDate;
    int duration;
    List<Calendar> doneDates;

    public Addiction(long id, String text, Calendar startDate, Calendar endDate, int duration, List<Calendar> doneDates) {
        this.id = id;
        this.text = text;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.doneDates = doneDates;
    }

    public Addiction(String text, Calendar startDate, int duration) {
        this.id = 0; //до сохранения в реляционке сделаем заглушку.
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

    public void setText(String text) {
        this.text = text;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
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

    public void setDoneDates(List<Calendar> doneDates) {
        this.doneDates = doneDates;
    }

    private Calendar getEndDate (Calendar startDate, int duration) {
        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.DAY_OF_MONTH, duration);
        return endDate;
    }
}
