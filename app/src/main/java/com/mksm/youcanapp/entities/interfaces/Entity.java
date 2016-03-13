package com.mksm.youcanapp.entities.interfaces;

import com.mksm.youcanapp.database.interfaces.Handler;

/**
 * Created by Техно on 06.03.2016.
 */
public interface Entity {
    Handler getHandler();

    void save();

    void delete();
}
