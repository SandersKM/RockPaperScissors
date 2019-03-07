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
        final Flag flag = new Flag();
        startServer(new ServerListener(){
            @Override
            public void response(String msg) {
                flag.setOk(testMsg.equals(msg));
            }
        });

        Socket test = new Socket("localhost", Server.APP_PORT);
        Communication.sendOver(test, testMsg);
        flag.busyWait();
        assertTrue(flag.isOk());
    }

    public void startServer(final ServerListener thingToDo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server.get().addListener(thingToDo);
                    Server.get().listenOnce().start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void test_game_invite_listener() {

    }

    @Test
    public void test_decline_invite_listener() throws IOException {
        final Flag flag = new Flag();
        startServer(new InviteListener() {
            @Override
            void handleDecline() {
                flag.setOk(true);
            }

            @Override
            void handleAccept() {
                flag.setOk(false);
            }
        });

        Socket socket = new Socket("localhost", Server.APP_PORT);
        Communication.sendOver(socket, "no\n");
        flag.busyWait();
        assertTrue(flag.isOk());
    }

    public void test_accept_invite_listener() {

    }

    public void test_move_listener() {

    }

    public void test_set_move_sent() {

    }
    public void test_set_move_Received() {

    }
    public void test_set_myMove() {
        Game g = new Game();
        g.setMyMove(Move.Rock);
        assertEquals(Move.Rock,g.getMyMove());

    }


}
