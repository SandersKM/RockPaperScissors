package com.example.rockpaperscissors;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import java.net.Socket;

public class DialogBox_Invitation implements DialogBox{

    DialogBoxView dialogBoxView;
    Context context;

    public DialogBox_Invitation(Context context, String IP){
        this.context = context;
        this.dialogBoxView = new DialogBoxView(context);
        setText(IP);
        dialogBoxView.showDialog();
        setButtonListeners() ;
    }

    @Override
    public void setText(String IP) {
        dialogBoxView.setIpText(IP);
        dialogBoxView.setMessageText("has invited you to play Rock Paper Scissors!");
        dialogBoxView.setNoButtonText("DECLINE");
        dialogBoxView.setYesButtonText("ACCEPT");
    }

    @Override
    public void setButtonListeners() {
        dialogBoxView.getNo().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResponse("no",SocketEchoThread.opponentIP, 8888);
                dialogBoxView.getDialog().dismiss();
            }
        });
        dialogBoxView.getYes().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResponse("yes",SocketEchoThread.opponentIP, 8888);
                dialogBoxView.getDialog().dismiss();
                toGameActivity();
            }
        });
    }

    private void toGameActivity(){
        Intent forwardIntent = new Intent(context, GameActivity.class);
        context.startActivity(forwardIntent);
    }

    public void sendResponse(final String message, final String host, final int port) {
        new Thread() {
            @Override
            public void run() {

                try {
                    Socket target = new Socket(host, port);
                    Communication.sendOver(target, message);
                    target.close();
                } catch (final Exception e) {
                    /*MainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Utilities.notifyException(MainActivity.this, e);
                        }
                    });*/
                }

            }
        }.start();
    }
}
