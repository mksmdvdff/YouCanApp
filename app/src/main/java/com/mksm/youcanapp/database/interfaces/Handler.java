package com.mksm.youcanapp.database.interfaces;

import java.util.Map;

/**
 * Created by mskm on 28.01.2016.
 */
public interface Handler {
    Map<String, String> getColumns();
    String getTableName();
}
