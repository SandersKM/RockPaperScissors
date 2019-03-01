package com.example.rockpaperscissors;

public enum Results {
    TIMEOUT_Other ("ran out of time. You win by default!"),
    TIMEOUT_THIS ("wins by default. You took too long!"),
    WIN ("lost. Congratulations!"),
    LOSE ("won. Better luck next time!"),
    TIE ("tied with you!");


    private final String text;

    Results(String text){
        this.text = text;
    }

    public String getText(){ return text; };

}
