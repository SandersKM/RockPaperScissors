package com.example.rockpaperscissors;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by gabriel on 2/15/19.
 */

public class SocketEchoThread extends Thread {
    private Socket socket;
    private ArrayList<ServerListener> listeners = new ArrayList<>();

    public SocketEchoThread(Socket socket, ArrayList<ServerListener> listeners) {
        this.socket = socket;
        this.listeners.addAll(listeners);
    }

    @Override
    public void run() {
        try {
            String msg = Communication.receive(socket);
            //Communication.sendOver(socket, msg+"Sender");
            socket.close();
            for (ServerListener listener: listeners) {
                listener.notifyMessage(msg);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}