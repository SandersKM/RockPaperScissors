package com.example.rockpaperscissors;

public class MoveData {
    String opponentIP;
    Move move;

    public MoveData(String ip, Move move) {
        this.opponentIP = ip;
        this.move = move;
    }

    public Move getMove() {
       return move;
    }

    public String getOpponentIP() {
        return opponentIP;
    }

    @Override
    public String toString(){
        return opponentIP + "::" + move.toString();
    }

}
