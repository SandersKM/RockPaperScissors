package com.example.rockpaperscissors;

public class Game {
    static Move myMove, otherMove;
    static boolean moveSent = false;
    static boolean moveReceived = false;
    static boolean turnCompleted = false;


    public static Move getMyMove() {return myMove;}
    public static void setMyMove (Move m) {myMove = m;}

    public static Move getOtherMove() {return otherMove;}
    public static void setOtherMove (Move m) {myMove = m; }

    public static void setMoveSent(boolean value) {moveSent = value; }
    public static boolean getMoveSent() {return moveSent; }

    public static void setMoveReceived(boolean value) {moveReceived = value; }
    public static boolean getMoveReceived() {return moveReceived; }

    public static void setturnCompleted(boolean value) {turnCompleted = value; }
    public static boolean getTurnCompleted() {return turnCompleted; }
}
