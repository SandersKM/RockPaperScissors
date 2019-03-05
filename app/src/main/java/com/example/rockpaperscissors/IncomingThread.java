package com.example.rockpaperscissors;

import android.util.Log;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by gabriel on 2/15/19.
 */

public class IncomingThread extends Thread {
    private Socket socket;
    private ArrayList<ServerListener> listeners = new ArrayList<>();


    public IncomingThread(Socket socket, ArrayList<ServerListener> listeners) {
        this.socket = socket;
        this.listeners.addAll(listeners);
    }

    @Override
    public void run() {
        try {
            String msg = Communication.receive(socket);
            socket.close();
            for (ServerListener listener: listeners) {
                listener.response(msg);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}