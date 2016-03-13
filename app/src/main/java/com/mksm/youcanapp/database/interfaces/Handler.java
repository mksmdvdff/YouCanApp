package com.mksm.youcanapp.database.interfaces;

import com.mksm.youcanapp.entities.interfaces.Entity;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by mskm on 28.01.2016.
 */
public interface Handler extends DatabaseTable {
    void save(Entity entity);
    void delete(Entity entity);
    Entity getEntity(long id);
    List<Entity> getEntitiesByDate(Calendar date);
}
