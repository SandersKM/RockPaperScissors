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
                    currentGame.setMoveReceived((true));
                    Log.e(MainActivity.class.getName(), "check to see if message is a move:"+msg);
                    //   Log.e(MainActivity.class.getName(),Move.valueOf(msg.replace("\n","")).toString() );
                    currentGame.setOtherMove(Move.valueOf(msg.replace("\n","")));
                    //    Log.e(MainActivity.class.getName(), "I got a "+Game.getOtherMove().toString());
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server.get().addListener(incomingMove);
                    Server.get().listen();
                } catch (IOException e) {
                    Log.e(MainActivity.class.getName(), "Could not start server");
                }
            }
        }).start();


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
                    while (!(currentGame.getMoveSent() && currentGame.getMoveReceived())) {
                        Log.e(GameActivity.class.getName(), "Main game waiting loop moveSent:"+currentGame.getMoveSent()+
                                "   moveReceived:"+currentGame.getMoveReceived());
                   }
                    Log.e(GameActivity.class.getName(), "finished main game loop");
                    Log.e(GameActivity.class.getName(), currentGame.getMyMove().toString()+ " "+currentGame.getOtherMove().toString());
                    showResult(Moves.compareMoves(currentGame.getMyMove(),currentGame.getOtherMove()));
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
        DialogBox_GameResult resultDialog =  new DialogBox_GameResult(this.context, Server.getOpponentIP());
        resultDialog.setResult(result);
        resultDialog.showResults();
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
                currentGame.setMoveSent(true);
                currentGame.setMyMove(Move.Rock);
                Communication.send(Move.Rock.toString(),Server.getOpponentIP(),8888);
                Log.e(GameActivity.class.getName(), "I sent a rock");
                //showResult(getResult());
            }
        });
    }



    public void paperListener(){
        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                currentGame.setMoveSent(true);
                currentGame.setMyMove(Move.Paper);
                Communication.send(Move.Paper.toString(),Server.getOpponentIP(),8888);
                Log.e(GameActivity.class.getName(), "I sent paper");
                //showResult(getResult());
            }
        });
    }

    public void scissorsListener(){
        scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                currentGame.setMoveSent(true);
                currentGame.setMyMove(Move.Scissors);
                Communication.send(Move.Scissors.toString(),Server.getOpponentIP(),8888);
                Log.e(GameActivity.class.getName(), "I sent scissors");
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
