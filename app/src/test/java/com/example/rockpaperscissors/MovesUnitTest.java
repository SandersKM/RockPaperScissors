package com.example.rockpaperscissors;

import org.junit.Test;

import static com.example.rockpaperscissors.Move.Paper;
import static com.example.rockpaperscissors.Move.Rock;
import static com.example.rockpaperscissors.Move.Scissors;
import static org.junit.Assert.*;


public class MovesUnitTest extends Moves{

    Move myMove;
    Move otherMove;
    Move[] moveList = new Move[3];
    moveList[0] = Rock;
    moveList[1] = Paper;
    moveList[2] = Move.Scissors;

    @Test
    public void isQuitWorking() {
        myMove=Move.Quit;
        for (int x=0; x !=3; x++) {
            otherMove = moveList[x];
            compareMoves(myMove,otherMove);

            // Find a way to check if the Results object from compareMoves() is being output correctly
            // assertEquals()?
            // ask Dr. Ferrer in meeting
        }
    }

    @Test
    public void isTieWorking() {
        for (int x=0; x !=3; x++) {
            myMove = moveList[x];
            otherMove = moveList[x];
            compareMoves(myMove,otherMove);

            // See comment at isQuitWorking
        }
    }

    @Test
    public void isWinWorking(){
        for (int x=1; x !=3; x++) {
            myMove = moveList[x];
            otherMove = moveList[x - 1];
            compareMoves(myMove, otherMove);
            // See comment at isQuitWorking
        }
        myMove=Rock;
        otherMove=Scissors;
        compareMoves(myMove, otherMove);
        // See comment at isQuitWorking
    }

    @Test
    public void isLoseWorking(){
        for (int x=0; x !=3; x++) {
            myMove = moveList[x];
            otherMove = moveList[x+1];
            compareMoves(myMove, otherMove);
            // See comment at isQuitWorking
        }
        myMove=Scissors;
        otherMove=Paper;
        compareMoves(myMove, otherMove);
        // See comment at isQuitWorking
    }






















}
