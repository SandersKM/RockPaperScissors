package com.example.rockpaperscissors;

import android.content.Context;
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
        initializeServerListeners();
        setServerListeners();
        moveListener();
        currentGame = new Game();
        countdown();
        playGame();
    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            Communication.send(Move.Quit.toString(), Server.get().getOpponentIP(), Server.APP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                    }
                    GameActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showResult(Moves.compareMoves(currentGame.getMyMove(),
                                    currentGame.getOtherMove()));
                        }
                    });
                } catch (Exception e) {
                    Log.e(GameActivity.class.getName(), "Could not start game thread");
                }
            }
        }).start();
    }

    // // // // // // // // // //
    //  User Interface Code    //
    // // // // // // // // // //

    public void moveListener(){
        rockListener();
        paperListener();
        scissorsListener();
    }

    public void rockListener(){
        rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                currentGame.setMyMove(Move.Rock);
                disableButtons();
                try {
                    Communication.send(Move.Rock.toString(),
                            Server.get().getOpponentIP(),Server.APP_PORT);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void paperListener(){
        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                currentGame.setMyMove(Move.Paper);
                disableButtons();
                try {
                    Communication.send(Move.Paper.toString(),
                            Server.get().getOpponentIP(),Server.APP_PORT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void scissorsListener(){
        scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                currentGame.setMyMove(Move.Scissors);
                disableButtons();
                try {
                    Communication.send(Move.Scissors.toString(),
                            Server.get().getOpponentIP(),Server.APP_PORT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void disableButtons(){
        rock.setEnabled(false);
        paper.setEnabled(false);
        scissors.setEnabled(false);
    }

    public void countdown(){
        countDownTimer = new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("Countdown:  " + millisUntilFinished / 1000 );
            }

            public void onFinish() {
                showResult(Results.TIMEOUT_THIS);
                try {
                    Communication.send(Move.Quit.toString(),
                            Server.get().getOpponentIP(), Server.APP_PORT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // // // // // // // //
    //  Server   Code    //
    // // // // // // // //
    private void setServerListeners() {
        try {
            Server.get().clearListeners();
            Server.get().addListener(incomingMove);
        } catch (IOException e) {
            Log.e(GameActivity.class.getName(), "Could not start server");
        }
    }

    private void initializeServerListeners() {
        incomingMove = new ServerListener() {
            @Override
            public void response(String msg) {
                if (messageIsMove(msg.replace("\n",""))) {
                    currentGame.setOtherMove(Move.valueOf(msg.replace("\n","")));
                }
            }
        };
    }

    private boolean messageIsMove(String msg) {
        for (Move m : Move.values()) {
            if (m.toString().equals(msg)) {
                return true;
            }
        }
        return false;
    }

    private void showResult(Results result) {
        DialogBox_GameResult resultDialog = null;
        try {
            resultDialog = new DialogBox_GameResult(this.context, Server.get().getOpponentIP());
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultDialog.setResult(result);
        resultDialog.showResults();
    }

}
