package com.example.rockpaperscissors;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class DialogBox_Invitation implements DialogBox{

    DialogBoxView dialogBoxView;
    Context context;

    public DialogBox_Invitation(Context context, String IP){
        this.context = context;
        this.dialogBoxView = new DialogBoxView(context);
        setText(IP);
        dialogBoxView.showDialog();
        setButtonListeners();
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
                dialogBoxView.getDialog().dismiss();
            }
        });
        dialogBoxView.getYes().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forwardIntent = new Intent(context, GameActivity.class);
                context.startActivity(forwardIntent);
            }
        });
    }
}
