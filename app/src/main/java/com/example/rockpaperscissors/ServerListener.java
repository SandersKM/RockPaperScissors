package com.example.rockpaperscissors;

import java.net.Socket;

public interface ServerListener {
    public void notifyMessage(String msg);
    public String getIP(Socket socket);

}



