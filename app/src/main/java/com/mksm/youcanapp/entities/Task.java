package com.mksm.youcanapp.entities;

import java.util.Calendar;

/**
 * Created by mskm on 24.01.2016.
 */
public class Task {

    int id;
    String text;
    State state;
    Calendar date;

    public Task(int id, String text, Calendar date, State state) {
        this.id = id;
        this.text = text;
        this.state = state;
        this.date = date;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public enum State {
        NOT_DONE("NOT_DONE"),
        DONE("DONE"),
        TRANSFERED("TRANSFERED");


        private String id;

        State(String id) {
            this.id = id;
        }

        String getId() {
            return this.id;
        }

        public static State getById(String id) {
           if (id==null || id.isEmpty()) {
               return null;
           }
           for (State state : State.values()) {
               if (state.id.equals(id))
                   return state;
           }
            return null;
        }
    }
}
