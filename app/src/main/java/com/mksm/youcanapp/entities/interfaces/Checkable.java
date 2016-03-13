package com.mksm.youcanapp.entities.interfaces;

/**
 * Created by Техно on 07.02.2016.
 */
public interface Checkable extends Entity {
    void markChecked();
    void markUnchecked();
    String getText();
    boolean isChecked();

    void save();
}
