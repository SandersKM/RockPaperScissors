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
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    final Context context = this;
    Button invite, keyboard_dot, keyboard_back;
    Button[] digitKeys;
    TextView my_IP, other_IP;
    ServerListener gameInvite, acceptInvite, declineInvite, inviteExample;
    Keyboard keyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findIDs();
        setMy_IP();
        setupKeyboard();
        inviteOtherPlayer();
        initializeServerListeners();
        setServerListeners();
    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(false);
    }


    public void findIDs(){
        setContentView(R.layout.activity_main);
        invite = findViewById(R.id.request_button);
        my_IP = findViewById(R.id.IP_device);
        other_IP = findViewById(R.id.IP_textbox);
    }


    public void setMy_IP(){
        try {
            my_IP.setText(Utilities.getLocalIpAddress());
        } catch (SocketException e) {
            Log.e("MainActivity", "Threw exception when finding ip address");
        }
    }


    private void inviteOtherPlayer() {
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Communication.send("PlayRockPaperScissors", other_IP.getText().toString(), Server.APP_PORT);
                setWaitingForResponse();
            }
        });
    }

    // // // // // // // //
    //  Client-Server    //
    // // // // // // // //
    private void initializeServerListeners() {
        gameInviteListener();
        acceptInviteListener();
        declineInviteListener();
    }

    public void setServerListeners() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server.get().clearListeners();
                    Server.get().addListener(gameInvite);
                    Server.get().addListener(acceptInvite);
                    Server.get().addListener(declineInvite);
                    Server.get().listen();
                } catch (IOException e) {
                    Log.e(MainActivity.class.getName(), "Could not start server");
                }
            }
        }).start();
    }

    private void gameInviteListener() {
        gameInvite = new ServerListener() {
            @Override
            public void response(String msg) {
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
    }

    private void acceptInviteListener() {
        acceptInvite = new ServerListener() {
            @Override
            public void response(String msg) {
                if (msg.equals("yes\n")) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Server.get().setOpponentIP(Server.get().getIncomingIP());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            toGameActivity();
                        }
                    });
                }
            }
        };
    }


    private void declineInviteListener() {
        declineInvite = new DeclineInviteListener() {
            @Override
            void handleDecline() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setDeclined();
                    }
                });
            }
        };
    }

    private void inviteListener() {
        inviteExample = new InviteListener() {
            @Override
            void handleDecline() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setDeclined();
                    }
                });
            }

            @Override
            void handleAccept() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Server.get().setOpponentIP(Server.get().getIncomingIP());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        toGameActivity();
                    }
                });
            }
        };
    }

    private void initAcceptInvite(final DialogBox_Waiting waitingBox) {
        acceptInvite = new ServerListener() {
            @Override
            public void response(String msg) {
                if (msg.equals("yes\n")) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toGameActivity();
                            waitingBox.dialogBoxView.getDialog().dismiss();
                        }
                    });
                }
            }
        };
    }

    private void initDeclineInvite(final DialogBox_Waiting waitingBox) {
        declineInvite = new ServerListener() {
            @Override
            public void response(String msg) {
                if (msg.equals("no\n")) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            waitingBox.dialogBoxView.getDialog().dismiss();
                            setDeclined();
                        }
                    });
                }
            }
        };
    }

    // // // // // // // //
    //  Dialog Box Code  //
    // // // // // // // //
    // https://www.mkyong.com/android/android-custom-dialog-example/
    private void setInvitation() {
        try {
            new DialogBox_Invitation(context, Server.get().getIncomingIP());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setWaitingForResponse(){
        DialogBox_Waiting waitingBox = new DialogBox_Waiting(context, other_IP.getText().toString());
        initAcceptInvite(waitingBox);
        initDeclineInvite(waitingBox);
    }



    private void setDeclined(){
        new DialogBox_Declined(context, other_IP.getText().toString());
    }

    private void toGameActivity(){
        Intent forwardIntent = new Intent(context, GameActivity.class);
        context.startActivity(forwardIntent);
    }



    // // // // // // // //
    //  Keyboard Code    //
    // // // // // // // //
    private void setupKeyboard(){
        keyboard = new Keyboard(this);
    }
}
