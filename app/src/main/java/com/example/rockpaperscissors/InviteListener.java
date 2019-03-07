package com.example.rockpaperscissors;

abstract public class InviteListener implements ServerListener {

    @Override
    public void response(String msg) {
        if (msg.equals("no\n")) {
            handleDecline();
        } else if (msg.equals("yes\n")) {
            handleAccept();
        }
    }

    abstract void handleDecline();
    abstract void handleAccept();
}
