package com.mksm.youcanapp.database.interfaces;

import com.mksm.youcanapp.entities.Task;

import java.util.Calendar;
import java.util.List;

/**
 * Created by mskm on 24.01.2016.
 */
public interface TaskHandler extends Handler {
    Task getTaskById(String _id);
    List<Task> getTasksByDate(Calendar date);
    void addNewTask (Task task);
    void addNewTasks (List<Task> tasks);
    void updateTask (Task task);
    /*
    * Marked "DONE" task mustn't be deleted, just change it status
     */
    void deleteTask (Task task);
    void deleteAll();
}
