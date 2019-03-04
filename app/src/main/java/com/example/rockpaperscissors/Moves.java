package com.example.rockpaperscissors;

import android.util.Log;

enum Move {Rock, Paper, Scissors, Quit, Empty}

public class Moves {

    public static Results compareMoves(Move myMove, Move otherMove) {
        if (myMove.equals(Move.Quit)) {
            return Results.TIMEOUT_THIS;
        }
        //popup ties
        if ((myMove.equals(Move.Rock) && otherMove.equals(Move.Rock))
            || (myMove.equals(Move.Paper) && otherMove.equals(Move.Paper))
            || (myMove.equals(Move.Scissors) && otherMove.equals(Move.Scissors))){
            return Results.TIE;
            }
        //popup wins
        if ((myMove.equals(Move.Rock) && otherMove.equals(Move.Scissors))
            || (myMove.equals(Move.Paper) && otherMove.equals(Move.Rock))
            || (myMove.equals(Move.Scissors) && otherMove.equals(Move.Paper))){
            return Results.WIN;
            }
        //popup losses
        if ((myMove.equals(Move.Rock) && otherMove.equals(Move.Paper))
            || (myMove.equals(Move.Paper) && otherMove.equals(Move.Scissors))
            || (myMove.equals(Move.Scissors) && otherMove.equals(Move.Rock))){
            return Results.LOSE;
            }
        return Results.TIMEOUT_Other;
    }
}
