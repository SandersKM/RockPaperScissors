package com.example.rockpaperscissors;

public class Flag {
    private boolean isSet = false;
    private boolean isOk = false;

    public boolean isSet() {return isSet;}
    public boolean isOk() {return isOk;}

    public void setOk(boolean test) {
        isSet = true;
        isOk = test;
    }

    public void busyWait() {
        while (!isSet) {}
    }
}
