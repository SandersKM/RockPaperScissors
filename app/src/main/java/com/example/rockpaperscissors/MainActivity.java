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
    Server s;
    TextView my_IP, other_IP, other_port, incoming, test;
    ServerListener gameInvite, acceptInvite, declineInvite, incomingMove;
    IP_AddressEditor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findIDs();
        setMy_IP();
        setupKeyboard();
        inviteOtherPlayer();
        initializeServerListeners();
        startListeners();
    }

    public void findIDs(){
        setContentView(R.layout.activity_main);
        invite = findViewById(R.id.request_button);
        my_IP = findViewById(R.id.IP_device);
        other_IP = findViewById(R.id.IP_textbox);
        digitKeys = new Button[10];
        digitKeys[0] = findViewById(R.id.zero);
        digitKeys[1] = findViewById(R.id.one);
        digitKeys[2] = findViewById(R.id.two);
        digitKeys[3] = findViewById(R.id.three);
        digitKeys[4] = findViewById(R.id.four);
        digitKeys[5] = findViewById(R.id.five);
        digitKeys[6] = findViewById(R.id.six);
        digitKeys[7] = findViewById(R.id.seven);
        digitKeys[8] = findViewById(R.id.eight);
        digitKeys[9] = findViewById(R.id.nine);
        keyboard_dot = findViewById(R.id.dot);
        keyboard_back = findViewById(R.id.back);
    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(false);
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
    //  Dialog Box Code  //
    // // // // // // // //


    // https://www.mkyong.com/android/android-custom-dialog-example/
    private void setInvitation() {
        try {
            new DialogBox_Invitation(context, Server.get().getOpponentIP());
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        acceptInvite = new ServerListener() {
            @Override
            public void response(String msg) {
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
            public void response(String msg) {
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

    }

    // // // // // // // //
    //  Keyboard Code    //
    // // // // // // // //


    private void setupKeyboard(){
        this.editor = new IP_AddressEditor();
        setListeners();
        setAbilities();
    }

    private void setListeners() {
        setDigitsListener();
        setDotListener();
        setBackspaceListener();
    }

    private void setBackspaceListener() {
        keyboard_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.backspace();
                updateView();
            }
        });
    }

    private void setDotListener() {
        keyboard_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.addDot();
                updateView();
            }
        });
    }

    private void setDigitsListener() {
        for(final Button digit: digitKeys){
            digit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.addDigit(digit.getText().toString());
                    updateView();
                }
            });
        }
    }

    private void updateView() {
        other_IP.setText(editor.getIP());
        setAbilities();
    }

    private void setAbilities(){
        pressInviteAbility();
        pressDigitAbility();
        pressDotAbility();
    }

    private void pressDotAbility() {
        if(editor.canAddDot()){
            keyboard_dot.setEnabled(true);
        }
        else{
            keyboard_dot.setEnabled(false);
        }
    }

    private void pressDigitAbility() {
        if(editor.consecutiveDigitLimitReached()){
            setDigitAbility(false);
        }
        else{
            setDigitAbility(true);
        }
    }

    private void setDigitAbility(boolean state) {
        for(final Button digit: digitKeys){
            digit.setEnabled(state);
        }
    }

    private void pressInviteAbility() {
        if(editor.isValidIP()){
            invite.setEnabled(true);
        }
        else{
            invite.setEnabled(false);
        }
    }
}
