package com.example.rockpaperscissors;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class GameActivity extends AppCompatActivity {

    final Context context = this;
    Button rock, paper, scissors;
    private TextView timer;
    Game currentGame;
    ServerListener incomingMove;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findIDs();
        incomingMove = new ServerListener() {
            @Override
            public void notifyMessage(String msg) {
                if (messageIsMove(msg.replace("\n",""))) {
                    Log.e(GameActivity.class.getName(), "check to see if message is a move:"+msg);
                    Log.e(GameActivity.class.getName(),Move.valueOf(msg.replace("\n","")).toString() );
                    currentGame.setOtherMove(Move.valueOf(msg.replace("\n","")));
                    currentGame.setMoveReceived((true));
                    Log.e(GameActivity.class.getName(), "I got a "+currentGame.getOtherMove().toString());
                }
            }
        };


        try {
            Server.get().addListener(incomingMove);
        } catch (IOException e) {
            Log.e(GameActivity.class.getName(), "Could not start server");
        }

        moveListener();
        currentGame = new Game();
        countdown(); // How will we restart this with playagain? reinitialize this screen?
        playGame();
    }

    public void findIDs(){
        setContentView(R.layout.activity_game);
        rock = findViewById(R.id.stone_button);
        paper = findViewById(R.id.paper_button);
        scissors = findViewById(R.id.scissors_button);
        timer = findViewById(R.id.timerView);
    }


    private void playGame() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (currentGame.getMyMove().equals(Move.Empty)
                            || currentGame.getOtherMove().equals(Move.Empty)) {
                        Log.e(GameActivity.class.getName(), "Game waiting loop myMove:"+currentGame.getMyMove().toString()+
                                " otherMove:"+currentGame.getOtherMove().toString());
                    }
                    Log.e(GameActivity.class.getName(), "finished main game loop");
                    Log.e(GameActivity.class.getName(), currentGame.getMyMove().toString()+ " "+currentGame.getOtherMove().toString());
                    Log.e(GameActivity.class.getName(), Moves.compareMoves(currentGame.getMyMove(),currentGame.getOtherMove()).toString());
                    showResult(Moves.compareMoves(currentGame.getMyMove(),currentGame.getOtherMove()));
                    Log.e(GameActivity.class.getName(), "Main game waiting loop moveSent:"+currentGame.getMoveSent()+
                            "   moveReceived:"+currentGame.getMoveReceived());
                } catch (Exception e) {
                    Log.e(GameActivity.class.getName(), "Could not start game thread");
                }
            }
        }).start();
    }


    // These next 3 boolean methods might be able to be moved into a static Game class to make our code cleaner
    public boolean moveSent() {
        // if a move is sent
        // I do not know how to check for/catch when we send a move
        // Please fix if you do
        // or alternatively tell me(Rader) why I'm dumb
        return true;
    }

    public boolean moveReceived() {
        // see moveSent above for the same situation :)
        return true;
    }

    public boolean turnComplete() {
        if (moveSent() && moveReceived()) {
            return true;
        }
        return false;
    }


    public void moveListener(){
        rockListener();
        paperListener();
        scissorsListener();
    }

    private void showResult(Results result) {
        Log.e(GameActivity.class.getName(), "about to make results pop up object");
        //KATE I think the next line of code is throwing the error
        DialogBox_GameResult resultDialog = null;
        resultDialog = new DialogBox_GameResult(this.context, Server.getOpponentIP());
        Log.e(GameActivity.class.getName(), "about to set results for the dialog");
        resultDialog.setResult(result);
        Log.e(GameActivity.class.getName(), "this next method is called showresults inside showresults?");
        resultDialog.showResults();
        Log.e(GameActivity.class.getName(), "all done");
    }


    private Results getResult() {
        // TODO find the result
        Results result = Results.LOSE;
        return result;
    }


    // Depending on what needs to be sent, we could make these into one generic method,
    // but I figured we should keep it "simpler" for now by having separate methods
    public void rockListener(){
        rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                currentGame.setMyMove(Move.Rock);
                Communication.send(Move.Rock.toString(),Server.getOpponentIP(),8888);
                Log.e(GameActivity.class.getName(), "I sent a rock");
                currentGame.setMoveSent(true);
                //showResult(getResult());
            }
        });
    }



    public void paperListener(){
        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                currentGame.setMyMove(Move.Paper);
                Communication.send(Move.Paper.toString(),Server.getOpponentIP(),8888);
                Log.e(GameActivity.class.getName(), "I sent paper");
                currentGame.setMoveSent(true);
                //showResult(getResult());
            }
        });
    }

    public void scissorsListener(){
        scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                currentGame.setMyMove(Move.Scissors);
                Communication.send(Move.Scissors.toString(),Server.getOpponentIP(),8888);
                Log.e(GameActivity.class.getName(), "I sent scissors");
                currentGame.setMoveSent(true);
                //showResult(getResult());
            }
        });
    }

    public void countdown(){
        countDownTimer = new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("Countdown:  " + millisUntilFinished / 1000 );
            }

            public void onFinish() {
                showResult(Results.TIMEOUT_THIS);
                // Notify other player that you lose
                Communication.send(Move.Quit.toString(), Server.getOpponentIP(), 8888);
            }
        }.start();
    }


    private boolean messageIsMove(String msg) {
        for (Move m : Move.values()) {
            //Log.e(MainActivity.class.getName(), "check for move:"+m.toString().length() + " vs "+msg.length());
            if (m.toString().equals(msg)) {
                return true;
            }
        }
        return false;
    }


}
