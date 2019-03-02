package com.example.rockpaperscissors;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IP_Keyboard {

    Button[] digits;
    Button dot, backspace, invite;
    TextView view;
    IP_AddressEditor editor;

    public IP_Keyboard(TextView view, Button[] digits, Button dot, Button backspace, Button invite){
        this.digits = digits;
        this.dot = dot;
        this.backspace = backspace;
        this.view = view;
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
        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.backspace();
                updateView();
            }
        });
    }

    private void setDotListener() {
        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.addDot();
                updateView();
            }
        });
    }

    private void setDigitsListener() {
        for(final Button digit: digits){
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
        view.setText(editor.getIP());
        setAbilities();
    }

    private void setAbilities(){
        pressInviteAbility();
        pressDigitAbility();
        pressDotAbility();
    }

    private void pressDotAbility() {
        if(editor.canAddDot()){
            dot.setEnabled(true);
        }
        else{
            dot.setEnabled(false);
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
        for(final Button digit: digits){
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
