package com.example.rockpaperscissors;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.net.SocketException;
import java.net.Socket;
import java.io.IOException;

// A comment

public class MainActivity extends AppCompatActivity {

    final Context context = this;
    Button invite;
    TextView my_IP, other_IP, other_port, incoming, test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findIDs();
        setMy_IP();
        inviteListener();
        startListener();


    }

    private void setDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialogue_main);
        Button dialogButtonAccept = (Button) dialog.findViewById(R.id.acceptButtonYes);
        Button dialogButtonDecline = (Button) dialog.findViewById(R.id.acceptButtonNo);
        dialogButtonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void startListener() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server s = new Server();
                    s.addListener(new ServerListener() {
                        @Override
                        public void notifyMessage(String msg) {
                               showIncoming(msg);
                        }
                    });
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
                send("PlayRockPaperScissors", other_IP.getText().toString(),
                        8888);
         //       Intent forwardIntent = new Intent(MainActivity.this, GameActivity.class);
         //       startActivity(forwardIntent);
            }
        });
    }

    public void findIDs(){
        setContentView(R.layout.activity_main);
        invite = findViewById(R.id.request_button);
        my_IP = findViewById(R.id.IP_device);
        other_IP = findViewById(R.id.IP_textbox);
        other_port = findViewById(R.id.Port_textbox);
        incoming  = findViewById(R.id.incoming);
        test = findViewById(R.id.button);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog();
            }
        });
    }

    public void setMy_IP(){
        try {
            my_IP.setText(Utilities.getLocalIpAddress());
        } catch (SocketException e) {
            Log.e("MainActivity", "Threw exception when finding ip address");
        }
    }

    public void send(final String message, final String host, final int port) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Socket target = new Socket(host, port);
                    Communication.sendOver(target, message);
                    showIncoming(Communication.receive(target));
                    target.close();
                } catch (final Exception e) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Utilities.notifyException(MainActivity.this, e);
                        }
                    });
                }

            }
        }.start();
    }

    private void showIncoming(final String msg) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                incoming.setText(msg);
            }
        });
    }


    // TODO overall stats and stats by player
    // TODO have a database of players in the network
    // TODO have a queue of games.
}
