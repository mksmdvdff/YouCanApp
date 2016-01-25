package com.mksm.youcanapp.entities;

/**
 * Created by mskm on 24.01.2016.
 */
public class Task {

    int id;
    String text;
    State state;

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

        State getById(String id) {
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
