package com.example.rockpaperscissors;

abstract public class DeclineInviteListener implements ServerListener {
    @Override
    public void response(String msg) {
        if (msg.equals("no\n")) {
            handleDecline();
        }
    }

    abstract void handleDecline();
}
