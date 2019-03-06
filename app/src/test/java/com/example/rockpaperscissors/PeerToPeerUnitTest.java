package com.example.rockpaperscissors;

import org.junit.Test;
import java.io.IOException;
import java.net.Socket;
import static org.junit.Assert.*;

public class PeerToPeerUnitTest {

    @Test
    public void test_send() throws Exception {
        final String testMsg = "This is a test.\nThis is only a test.\n";
        String result;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server.get().addListener(new ServerListener(){

                        @Override
                        public void response(String msg) {
                            //result = msg;
                        }
                    });
                    System.out.println("test1");
                    Server.get().listenOnce().start();
                    System.out.println("test2");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        System.out.println("test3");
        Socket test = new Socket("localhost", Server.APP_PORT);
        System.out.println("test4");
        Communication.sendOver(test, testMsg);
        System.out.println("test5"+testMsg);
        //System.out.println("test6"+result);
        //assertEquals(testMsg, result);
        System.out.println("test7");
    }



    public void test_game_invite_listener() {

    }

    public void test_decline_invite_listener() {

    }

    public void test_accept_invite_listener() {

    }

    public void test_move_listener() {

    }

    public void test_set_move_sent() {

    }
    public void test_set_move_Received() {

    }

}
