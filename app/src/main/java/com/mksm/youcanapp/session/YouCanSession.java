package com.mksm.youcanapp.session;

import java.util.Calendar;

/**
 * Created by Техно on 07.02.2016.
 */
public class YouCanSession {

    private static YouCanSession currSession;
    private static Calendar date;

    public YouCanSession(Calendar date) {
        this.date = date;
    }

    public static YouCanSession get() {
        return currSession;
    }

    public static void initNewSession(Calendar curDate){
        if (curDate == null || currSession != null)
            return;
        currSession = new YouCanSession(curDate);
    }

    public Calendar getDate() {
        return date;
    }
}
