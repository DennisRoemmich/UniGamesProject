package model;

public abstract interface Building {
    default int getIncomeFactor() {
        return 1;
    }

}