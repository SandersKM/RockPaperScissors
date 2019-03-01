package com.example.rockpaperscissors;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.net.SocketException;
import java.net.Socket;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    final Context context = this;
    Button invite;
    TextView my_IP, other_IP, other_port, incoming, test;
    ServerListener gameInvite, acceptInvite, declineInvite, incomingMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findIDs();
        setMy_IP();
        inviteListener();
        initializeServerListeners();
        startListeners();
    }

    public void findIDs(){
        setContentView(R.layout.activity_main);
        invite = findViewById(R.id.request_button);
        my_IP = findViewById(R.id.IP_device);
        other_IP = findViewById(R.id.IP_textbox);
        other_port = findViewById(R.id.Port_textbox);
        incoming  = findViewById(R.id.incoming);
        test = findViewById(R.id.button); // This is temporary!
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInvitation();            }
        });
    }

    public String getOther_IP() {
        return other_IP.toString();
    }

    public void setMy_IP(){
        try {
            my_IP.setText(Utilities.getLocalIpAddress());
        } catch (SocketException e) {
            Log.e("MainActivity", "Threw exception when finding ip address");
        }
    }

    // // // // // // // //
    //  Client-Server    //
    // // // // // // // //

    public void startListeners() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server s = new Server();
                    s.addListener(gameInvite);
                    s.addListener(acceptInvite);
                    s.addListener(declineInvite);
                    s.addListener(incomingMove);
                    s.listen();
                } catch (IOException e) {
                    Log.e(MainActivity.class.getName(), "Could not start server");
                }
            }
        }).start();
    }

    private void inviteListener() {
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Communication.send("PlayRockPaperScissors", other_IP.getText().toString(), 8888);
                setWaitingForResponse();
            }
        });
    }


    private void showIncoming(final String msg) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                incoming.setText(msg);
            }
        });
    }

    // // // // // // // //
    //  Dialog Box Code  //
    // // // // // // // //


    // https://www.mkyong.com/android/android-custom-dialog-example/
    private void setInvitation() {
        new DialogBox_Invitation(context, Server.getOpponentIP());
    }

    private void setWaitingForResponse(){
        new DialogBox_Waiting(context, other_IP.getText().toString());
    }

    private void setDeclined(){
        new DialogBox_Declined(context, other_IP.getText().toString());
    }

    private void toGameActivity(){
        Intent forwardIntent = new Intent(context, GameActivity.class);
        context.startActivity(forwardIntent);
    }

    private void initializeServerListeners() {
        gameInvite = new ServerListener() {
            @Override
            public void notifyMessage(String msg) {
                if (msg.equals("PlayRockPaperScissors\n")) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setInvitation();
                        }
                    });
                }
            }
        };

        acceptInvite = new ServerListener() {
            @Override
            public void notifyMessage(String msg) {
                if (msg.equals("yes\n")) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toGameActivity();
                        }
                    });
                }
            }
        };

        declineInvite = new ServerListener() {
            @Override
            public void notifyMessage(String msg) {
                if (msg.equals("no\n")) {

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setDeclined();
                        }
                    });
                }
            }
        };

        incomingMove = new ServerListener() {
            @Override
            public void notifyMessage(String msg) {
                if (messageIsMove(msg.replace("\n",""))) {
                    Game.setMoveReceived((true));
                    Log.e(MainActivity.class.getName(), "check to see if message is a move:"+msg);
                 //   Log.e(MainActivity.class.getName(),Move.valueOf(msg.replace("\n","")).toString() );
                    Game.setOtherMove(Move.valueOf(msg.replace("\n","")));
                //    Log.e(MainActivity.class.getName(), "I got a "+Game.getOtherMove().toString());
                }
            }
        };

    }

    private boolean messageIsMove(String msg) {
        for (Move m : Move.values()) {
            //Log.e(MainActivity.class.getName(), "check for move:"+m.toString().length() + " vs "+msg.length());
            if (m.toString().equals(msg)) {
                return true;
            }
        }
        return false;
    }


    // TODO overall stats and stats by player
    // TODO have a database of players in the network
    // TODO have a queue of games.
}
