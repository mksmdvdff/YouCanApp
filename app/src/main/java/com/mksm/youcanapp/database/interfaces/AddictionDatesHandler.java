package com.mksm.youcanapp.database.interfaces;

import java.util.Calendar;
import java.util.List;

/**
 * Created by mskm on 31.01.2016.
 */
public interface AddictionDatesHandler extends Handler {

    List<Calendar> getDates(long id);
    void addNewDate (long id, Calendar date);
}
