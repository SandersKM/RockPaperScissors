package com.example.rockpaperscissors;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

public class DialogBoxView {

    final Dialog dialog;
    TextView IP, message;
    Button yes, no;

    public DialogBoxView(Context context){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(false);
        getIDs();
    }

    public void showDialog(){
        dialog.show();
    }

    private void getIDs() {
        IP = dialog.findViewById(R.id.dialog_IP);
        message = dialog.findViewById(R.id.dialog_text);
        yes = dialog.findViewById(R.id.dialog_buttonYes);
        no = dialog.findViewById(R.id.dialog_buttonNo);
    }

    public void setIpText(String txt){
        IP.setText(txt);
    }

    public void setMessageText(String txt){
        message.setText(txt);
    }

    public void setYesButtonText(String txt){
        yes.setText(txt);
    }

    public void setNoButtonText(String txt){
        no.setText(txt);
    }

    public Dialog getDialog(){
        return dialog;
    }

    public Button getNo() {
        return no;
    }

    public Button getYes() {
        return yes;
    }
}
