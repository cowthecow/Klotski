package com.laserinfinite.java.move;

public class Move {
    private final int startLocation;
    private final int direction;

    public Move(int startLocation, int direction) {
        this.startLocation = startLocation;
        this.direction = direction;
    }

    public int startLocation() {
        return startLocation;
    }

    public int getDirection() {
        return direction;
    }

}
