package com.mksm.youcanapp.entities;

import java.util.Calendar;

/**
 * Created by mskm on 24.01.2016.
 */
public class Task {

    long id;
    String text;
    State state;
    Calendar date;

    public Task(long id, String text, Calendar date, State state) {
        this.id = id;
        this.text = text;
        this.state = state;
        this.date = date;
    }

    public Task(String text, Calendar date) {
        this.id = 0; //до сохранения в реляционке сделаем заглушку.
        this.text = text;
        this.date = date;
        this.state = State.NOT_DONE;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

        public String getId() {
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
