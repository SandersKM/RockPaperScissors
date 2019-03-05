package com.example.rockpaperscissors;

import org.junit.Test;

import static com.example.rockpaperscissors.Move.Paper;
import static com.example.rockpaperscissors.Move.Rock;
import static com.example.rockpaperscissors.Move.Scissors;
import static org.junit.Assert.*;


public class MovesUnitTest extends Moves{

    Move myMove;
    Move otherMove;
    Move[] moveList = new Move[]{Move.Rock, Move.Paper, Move.Scissors, Move.Rock};

    @Test
    public void isMyQuitWorking() {
        myMove=Move.Quit;
        for (int x=0; x !=3; x++) {
            otherMove = moveList[x];
            assertEquals(Results.TIMEOUT_THIS, compareMoves(myMove,otherMove));
        }
    }

    @Test
    public void isOtherQuitWorking() {
        otherMove=Move.Quit;
        for (int x=0; x !=3; x++) {
            myMove = moveList[x];
            assertEquals(Results.TIMEOUT_Other, compareMoves(myMove,otherMove));
        }
    }

    @Test
    public void isTieWorking() {
        for (int x=0; x !=3; x++) {
            myMove = moveList[x];
            otherMove = moveList[x];
            assertEquals(Results.TIE, compareMoves(myMove,otherMove));
        }
    }

    @Test
    public void isWinWorking(){
        for (int x=1; x !=4; x++) {
            myMove = moveList[x];
            otherMove = moveList[x - 1];
            assertEquals(Results.WIN, compareMoves(myMove, otherMove));
        }

    }

    @Test
    public void isLoseWorking(){
        for (int x=0; x !=3; x++) {
            myMove = moveList[x];
            otherMove = moveList[x+1];
            assertEquals(Results.LOSE, compareMoves(myMove, otherMove));
        }
    }






















}
