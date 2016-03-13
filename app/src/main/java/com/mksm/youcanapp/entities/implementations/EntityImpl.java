package com.mksm.youcanapp.entities.implementations;

import com.mksm.youcanapp.database.interfaces.Handler;
import com.mksm.youcanapp.entities.interfaces.Entity;

/**
 * Created by Техно on 03.03.2016.
 */
public abstract class EntityImpl implements Entity {
    private Handler handler;

    public EntityImpl(Handler handler) {
        this.handler = handler;
    }

    @Override
    public Handler getHandler() {
        return handler;
    }

    @Override
    public void save(){
        handler.save(this);
    }

    @Override
    public void delete(){
        handler.delete(this);
    }
}
