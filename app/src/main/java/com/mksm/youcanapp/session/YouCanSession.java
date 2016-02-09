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

    public static YouCanSession initNewSession(Calendar curDate){
        if (curDate == null)
            return null;
        currSession = new YouCanSession(curDate);
        return currSession;
    }

    public Calendar getDate() {
        return date;
    }
}
