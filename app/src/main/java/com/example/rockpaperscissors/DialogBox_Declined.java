package com.example.rockpaperscissors;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class DialogBox_Declined implements DialogBox{

    DialogBoxView dialogBoxView;
    Context context;

    public DialogBox_Declined(Context context, String IP){
        this.context = context;
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
                dialogBoxView.getDialog().dismiss();
                new DialogBox_Waiting(context, "IP_address");
                //Not sure about best way to get IP from textview in mainactivity here (Dan)
                //Communication.send("PlayRockPaperScissors", MainActivity.other_IP.getText().toString(), 8888);
            }
        });
        dialogBoxView.getYes().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBoxView.getDialog().dismiss();
            }
        });
    }
}
