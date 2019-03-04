package com.example.rockpaperscissors;

public class Game {
    Move myMove, otherMove;

    public Game(){
        myMove = Move.Scissors;   // change back to EMPTY . Just doing this to test with one device!!!!!
        otherMove = Move.Paper;  // change back to EMPTY . Just doing this to test with one device!!!!!
    }


    public Move getMyMove() {return myMove;}
    public void setMyMove (Move m) {myMove = m;}

    public Move getOtherMove() {return otherMove;}
    public void setOtherMove (Move m) {myMove = m; }

}
