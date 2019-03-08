package com.example.rockpaperscissors;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Keyboard {

    final Context context;
    Button invite, keyboard_dot, keyboard_back;
    Button[] digitKeys;
    TextView other_IP;
    IP_AddressEditor editor;
    Activity activity;

    public Keyboard(Activity activity){
        this.editor = new IP_AddressEditor();
        this.activity = (Activity) activity;
        this.context = activity.getApplicationContext();
        initializeButtons();
        setupKeyboard();
    }

    private void initializeButtons(){
        //this.activity.setContentView(R.layout.activity_main);
        other_IP = activity.findViewById(R.id.IP_textbox);
        digitKeys = new Button[10];
        digitKeys[0] = activity.findViewById(R.id.zero);
        digitKeys[1] =activity.findViewById(R.id.one);
        digitKeys[2] = activity.findViewById(R.id.two);
        digitKeys[3] = activity.findViewById(R.id.three);
        digitKeys[4] = activity.findViewById(R.id.four);
        digitKeys[5] = activity.findViewById(R.id.five);
        digitKeys[6] = activity.findViewById(R.id.six);
        digitKeys[7] = activity.findViewById(R.id.seven);
        digitKeys[8] = activity.findViewById(R.id.eight);
        digitKeys[9] = activity.findViewById(R.id.nine);
        keyboard_dot = activity.findViewById(R.id.dot);
        keyboard_back = activity.findViewById(R.id.back);
        invite = activity.findViewById(R.id.request_button);
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
    }
}


