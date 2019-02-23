package com.example.rockpaperscissors;

import android.content.Context;
import android.view.View;

public class DialogBox_Waiting implements DialogBox{

    DialogBoxView dialogBoxView;
    Context context;

    public DialogBox_Waiting(Context context, String IP){
        this.context = context;
        this.dialogBoxView = new DialogBoxView(context);
        setText(IP);
        dialogBoxView.showDialog();
        setButtonListeners();
    }

    @Override
    public void setText(String IP) {
        dialogBoxView.setIpText(IP);
        dialogBoxView.setMessageText("Waiting for response.");
        dialogBoxView.setNoButtonText("QUIT");
        dialogBoxView.getYes().setVisibility(View.GONE);
    }

    @Override
    public void setButtonListeners() {
        dialogBoxView.getNo().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBoxView.getDialog().dismiss();
            }
        });
    }
}
