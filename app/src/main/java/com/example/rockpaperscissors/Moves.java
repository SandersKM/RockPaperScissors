package com.example.rockpaperscissors;

enum Move {Rock, Paper, Scissors, Quit, Empty}

public class Moves {

    public static Results compareMoves(Move myMove, Move otherMove) {
        if (myMove.equals(Move.Quit)) return Results.TIMEOUT_THIS;
        if (isTie(myMove, otherMove)) return Results.TIE;
        if (isWin(myMove, otherMove)) return Results.WIN;
        if (isLose(myMove, otherMove)) return Results.LOSE;
        else return Results.TIMEOUT_Other;
    }

    public static Boolean isTie(Move myMove, Move otherMove) {
        if ((myMove.equals(Move.Rock) && otherMove.equals(Move.Rock))
                || (myMove.equals(Move.Paper) && otherMove.equals(Move.Paper))
                || (myMove.equals(Move.Scissors) && otherMove.equals(Move.Scissors))){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static Boolean isWin(Move myMove, Move otherMove) {
        if ((myMove.equals(Move.Rock) && otherMove.equals(Move.Scissors))
                || (myMove.equals(Move.Paper) && otherMove.equals(Move.Rock))
                || (myMove.equals(Move.Scissors) && otherMove.equals(Move.Paper))){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static Boolean isLose(Move myMove, Move otherMove) {
        if ((myMove.equals(Move.Rock) && otherMove.equals(Move.Paper))
                || (myMove.equals(Move.Paper) && otherMove.equals(Move.Scissors))
                || (myMove.equals(Move.Scissors) && otherMove.equals(Move.Rock))){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }






}
