package com.konoplyova.nastya;

public enum Point {
    X('X'),
    O('O'),
    EMPTY(' ');

    private final char point;

    Point(char initPoint) {
        this.point = initPoint;
    }

    public char getPoint() {
        return this.point;
    }
}
