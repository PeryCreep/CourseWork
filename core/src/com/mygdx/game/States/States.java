package com.mygdx.game.States;

public enum States {
    RUNNING(1) , PAUSED(2), STOPPED(3);
    private final int id;

    States(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
