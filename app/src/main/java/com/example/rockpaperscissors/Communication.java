package com.example.rockpaperscissors;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by gabriel on 2/18/19.
 */



public class Communication {

    public static void send(final String message, final String host, final int port) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Socket target = new Socket(host, port);
                    sendOver(target, message);
                    Log.e(Communication.class.getName(), "Sending message: "+message);
                    target.close();
                } catch (final Exception e) {
                    Log.e("Communication", "Could not send: "+ message);
                }
            }
        }.start();
    }


    public static void sendOver(Socket target, String message) throws IOException {
        PrintWriter sockout = new PrintWriter(target.getOutputStream());
        if (!message.endsWith("\n")) {
            message += "\n";
        }
        sockout.print(message);
        sockout.flush();
    }

    public static String receive(Socket target) throws IOException {
        BufferedReader sockin = new BufferedReader(new InputStreamReader(target.getInputStream()));
        while (!sockin.ready()) {}
        StringBuilder input = new StringBuilder();
        while (sockin.ready()) {
            input.append(sockin.readLine());
            input.append("\n");
        }
        return input.toString();
    }
}
