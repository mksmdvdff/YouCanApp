package com.mksm.youcanapp.database.interfaces;


import com.mksm.youcanapp.entities.Addiction;

import java.util.Calendar;
import java.util.List;

/**
 * Created by mskm on 31.01.2016.
 */
public interface AddictionHandler extends Handler {

    Addiction getAddictionById(long _id);
    List<Addiction> getAddictionsByDate(Calendar date);
    void addNewAddiction (Addiction addiction);
    void addNewAddictions (List<Addiction> addictions);
    void updateAddiction (Addiction addiction);
    void addNewDate (Addiction addiction, Calendar date);
    /*
    * Marked "DONE" task mustn't be deleted, just change it status
     */
    void deleteAddiction (Addiction addiction);
    void deleteAll();
    AddictionDatesHandler getDatesHandler();
}
