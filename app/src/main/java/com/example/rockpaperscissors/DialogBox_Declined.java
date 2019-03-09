package com.example.rockpaperscissors;

import android.content.Context;
import android.view.View;

import java.io.IOException;

public class DialogBox_Declined implements DialogBox{

    DialogBoxView dialogBoxView;
    Context context;
    String IP;

    public DialogBox_Declined(Context context, String IP){
        this.context = context;
        this.IP = IP;
        this.dialogBoxView = new DialogBoxView(context);
        setText(IP);
        dialogBoxView.showDialog();
        setButtonListeners();
    }

    @Override
    public void setText(String IP) {
        dialogBoxView.setIpText(IP);
        dialogBoxView.setMessageText("has declined your invitation.");
        dialogBoxView.setNoButtonText("Try Again");
        dialogBoxView.setYesButtonText("OK");
    }

    @Override
    public void setButtonListeners() {
        dialogBoxView.getNo().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
                new DialogBox_Waiting(context, IP);
                try {
                    Communication.send("PlayRockPaperScissors",
                            Server.get().getIncomingIP(), Server.APP_PORT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        dialogBoxView.getYes().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });
    }

    @Override
    public void exit() {
        dialogBoxView.getDialog().dismiss();
    }
}
