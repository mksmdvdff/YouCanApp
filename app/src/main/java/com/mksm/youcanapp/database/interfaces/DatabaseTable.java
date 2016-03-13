package com.mksm.youcanapp.database.interfaces;

import java.util.Map;

/**
 * Created by Техно on 09.03.2016.
 */
public interface DatabaseTable {
    Map<String, String> getColumns();
    String getTableName();
}
