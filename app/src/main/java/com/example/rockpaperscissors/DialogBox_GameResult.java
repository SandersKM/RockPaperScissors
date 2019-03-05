package com.example.rockpaperscissors;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class DialogBox_GameResult implements DialogBox{

    DialogBoxView dialogBoxView;
    Context context;
    String IP;

    public DialogBox_GameResult(Context context, String IP){
        this.context = context;
        this.dialogBoxView = new DialogBoxView(context);
        this.IP = IP;
    }

    @Override
    public void setText(String IP) {
        dialogBoxView.setIpText(IP);
        dialogBoxView.setNoButtonText("Quit");
        dialogBoxView.getYes().setVisibility(View.GONE);
    }

    @Override
    public void setButtonListeners() {
        dialogBoxView.getNo().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBoxView.getDialog().dismiss();
                toMainActivity();
            }
        });
        dialogBoxView.getYes().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBoxView.getDialog().dismiss();
                new DialogBox_Waiting(context, IP);
            }
        });
    }

    public void setResult(Results result){
        dialogBoxView.setMessageText(result.getText());
        setText(this.IP);
    }

    public void showResults(){
        dialogBoxView.showDialog();
        setButtonListeners();
    }

    private void toMainActivity(){
        Intent forwardIntent = new Intent(context, MainActivity.class);
        context.startActivity(forwardIntent);
    }
}
