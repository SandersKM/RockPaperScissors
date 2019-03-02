package com.example.rockpaperscissors;

public class Game {
    Move myMove, otherMove;
    boolean moveSent = false;
    boolean moveReceived = false;
    boolean turnCompleted = false;

    public Game(){
        myMove = Move.Empty;
        otherMove = Move.Paper;  // change back to EMPTY . Just doing this to test with one device!!!!!
    }


    public Move getMyMove() {return myMove;}
    public void setMyMove (Move m) {myMove = m;}

    public Move getOtherMove() {return otherMove;}
    public void setOtherMove (Move m) {myMove = m; }

    public void setMoveSent(boolean value) {moveSent = value; }
    public boolean getMoveSent() {return moveSent; }

    public void setMoveReceived(boolean value) {moveReceived = value; }
    public boolean getMoveReceived() {return moveReceived; }

    public  void setturnCompleted(boolean value) {turnCompleted = value; }
    public  boolean getTurnCompleted() {return turnCompleted; }
}
