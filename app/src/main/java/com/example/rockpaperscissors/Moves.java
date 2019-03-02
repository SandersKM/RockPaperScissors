package com.example.rockpaperscissors;

import android.util.Log;

//just adding something inhere to start work on send methods. Feel free to replace this
enum Move {Rock, Paper, Scissors, Quit, Empty}




// This class might be able to be moved into a static Game class to make our code cleaner

public class Moves {

// Modifying this to use Kate's Results enum (Dan)
//    public void compareMoves(Move myMove, Move otherMove) {
    public static Results compareMoves(Move myMove, Move otherMove) {
        //you quit
        if (myMove.equals(Move.Quit)) {
            //System.out.println("You LOSE because you're a quitter!");
            Log.e(Moves.class.getName(), "It's a "+Results.TIMEOUT_THIS.toString());
            return Results.TIMEOUT_THIS;
        }
        //popup ties
        if ((myMove.equals(Move.Rock) && otherMove.equals(Move.Rock))
            || (myMove.equals(Move.Paper) && otherMove.equals(Move.Paper))
            || (myMove.equals(Move.Scissors) && otherMove.equals(Move.Scissors))){

                // Here is where the dialog box call goes
            Log.e(Moves.class.getName(), "It's a "+Results.TIE.toString());
            return Results.TIE;
                //System.out.println("You TIED because " + otherMove.toString() + " is the same as " + myMove.toString() + "!");
            }
        //popup wins
        if ((myMove.equals(Move.Rock) && otherMove.equals(Move.Scissors))
            || (myMove.equals(Move.Paper) && otherMove.equals(Move.Rock))
            || (myMove.equals(Move.Scissors) && otherMove.equals(Move.Paper))){

                // Here is where the dialog box call goes
            Log.e(Moves.class.getName(), "It's a "+Results.WIN.toString());
            return Results.WIN;
                //System.out.println("You WIN because " + myMove.toString() + " beats" + otherMove.toString() + "!");
            }
        //popup losses
        if ((myMove.equals(Move.Rock) && otherMove.equals(Move.Paper))
            || (myMove.equals(Move.Paper) && otherMove.equals(Move.Scissors))
            || (myMove.equals(Move.Scissors) && otherMove.equals(Move.Rock))){

                // Here is where the dialog box call goes
            Log.e(Moves.class.getName(), "It's a "+Results.LOSE.toString());
            return Results.LOSE;
                //System.out.println("You LOSE because " + otherMove.toString() + " beats" + myMove.toString() + "!");
            }
        // Display the quit/play again screen? or just call playAgain()?
        Log.e(Moves.class.getName(), "It's a "+Results.TIMEOUT_Other.toString());
        return Results.TIMEOUT_Other;
    }
}
