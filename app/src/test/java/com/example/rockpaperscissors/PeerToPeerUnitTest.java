package com.example.rockpaperscissors;

import org.junit.Test;
import java.io.IOException;
import java.net.Socket;
import static org.junit.Assert.*;

public class PeerToPeerUnitTest {


    @Test
    public void test_set_myMove() {
        Game g = new Game();
        g.setMyMove(Move.Rock);
        assertEquals(Move.Rock,g.getMyMove());
    }

    @Test
    public void test_set_otherMove() {
        Game g = new Game();
        g.setOtherMove(Move.Paper);
        assertEquals(Move.Paper,g.getOtherMove());
    }

    @Test
    public void test_send_receive() throws Exception {
        final String testMsg = "This is a test.\nThis is only a test.\n";
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


    public void startServer(final ServerListener thingToDo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server.get().addListener(thingToDo);
                    Server.get().listen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
