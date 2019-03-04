package com.example.rockpaperscissors;

/**
 * Created by gabriel on 2/15/19.
 */
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server  {

    public static final int APP_PORT = 8888;

    private static Server instance;

    public static Server get() throws IOException {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    private String opponentIP;
    private ServerSocket accepter;
    private ArrayList<ServerListener> listeners = new ArrayList<>();

    public Server() throws IOException {
        accepter = new ServerSocket(APP_PORT);
        opponentIP ="";
    }

    public void addListener(ServerListener listener) {
        this.listeners.add(listener);
    }

    public void listen() throws IOException {
        for (;;) {
            listenOnce().start();
        }
    }

    public SocketEchoThread listenOnce() throws IOException {
        Log.e(Server.class.getName(), "I heard something");
        Socket s = accepter.accept();
        opponentIP =  s.getInetAddress().toString().substring(1);
        SocketEchoThread echoer = new SocketEchoThread(s, listeners);
        return echoer;
    }

    public String getOpponentIP (){
        return opponentIP;
    }

    public void setOpponentIP (String ip) {
        opponentIP = ip;
    }

}
