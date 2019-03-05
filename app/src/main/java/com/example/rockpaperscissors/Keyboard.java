package com.example.rockpaperscissors;

import android.content.Context;
import android.view.View;
import android.widget.Button;

public class Keyboard {
    /*
    IP_AddressEditor editor;
    Context context;

    public Keyboard(Context context){
        this.editor = new IP_AddressEditor();
        this.context = context;
        setContentView(R.layout.activity_main);
        Button other_IP = findViewById(R.id.IP_textbox);
    }

    private void setupKeyboard(){
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
    }*/
}


