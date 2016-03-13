package com.mksm.youcanapp.database.interfaces;


import com.mksm.youcanapp.entities.implementations.Addiction;

import java.util.Calendar;
import java.util.List;

/**
 * Created by mskm on 31.01.2016.
 */
public interface AddictionHandler extends Handler {

    public void addNewDates(Addiction addiction, List<Calendar> dates);

}
