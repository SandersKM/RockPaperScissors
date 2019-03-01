package com.example.rockpaperscissors;

public enum Results {
    TIMEOUT ("ran out of time. You win by default!"),
    WIN ("lost. Congratulations!"),
    LOSE ("won. Better luck next time!"),
    TIE ("tied with you!");


    private final String text;

    Results(String text){
        this.text = text;
    }

    public String getText(){ return text; };

}
