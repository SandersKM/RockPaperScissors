package com.example.rockpaperscissors;

public class Game {
    Move myMove, otherMove;

    public Game(){
        myMove = Move.Empty;
        otherMove = Move.Paper;  //just for testing need to change back to Empty
    }


    public Move getMyMove() {return myMove;}
    public void setMyMove (Move m) {myMove = m;}

    public Move getOtherMove() {return otherMove;}
    public void setOtherMove (Move m) {myMove = m; }

}
