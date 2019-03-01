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
    CountDownTimer countDownTimer;
    ServerListener gameInvite, acceptInvite, declineInvite, incomingMove; //Not sure if we need these here too or not

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findIDs();
        moveListener();
        countdown(); // How will we restart this with playagain? reinitialize this screen?
        //adding a second server here because i dont thinkk the one in main activity is working while we are in game activity
        initializeServerListeners();
        startListeners();


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
                    while (!(Game.getMoveSent() && Game.getMoveReceived())) {
                        //Log.e(GameActivity.class.getName(), "Main game waiting loop moveSent:"+Game.getMoveSent()+"   moveReceived:"+Game.getMoveReceived());
                    }
                    showResult(Moves.compareMoves(Game.getMyMove(),Game.getOtherMove()));
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
        DialogBox_GameResult resultDialog =  new DialogBox_GameResult(this.context, "IP");
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
                Game.setMoveSent(true);
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
                Game.setMoveSent(true);
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

    //duplicating main activity server code to see if I need to run a 2nd server in game activity...
    public void startListeners() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server s = new Server();
                 //   s.addListener(gameInvite);
                 //   s.addListener(acceptInvite);
                 //   s.addListener(declineInvite);
                 //   s.addListener(incomingMove);
                    s.listen();
                } catch (IOException e) {
                    Log.e(GameActivity.class.getName(), "Could not start server");
                }
            }
        }).start();
    }

    private void initializeServerListeners() {
     /*   gameInvite = new ServerListener() {
            @Override
            public void notifyMessage(String msg) {
                if (msg.equals("PlayRockPaperScissors\n")) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setInvitation();
                        }
                    });
                }
            }
        };

        acceptInvite = new ServerListener() {
            @Override
            public void notifyMessage(String msg) {
                if (msg.equals("yes\n")) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toGameActivity();
                        }
                    });
                }
            }
        };

        declineInvite = new ServerListener() {
            @Override
            public void notifyMessage(String msg) {
                if (msg.equals("no")) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setDeclined();
                        }
                    });
                }
            }
        }; */

        incomingMove = new ServerListener() {
            @Override
            public void notifyMessage(String msg) {
                if (messageIsMove(msg)) {
                    Game.setMoveReceived((true));
                    Log.e(MainActivity.class.getName(), "movereceived:"+Game.getMoveReceived());
                    Game.setOtherMove(Move.valueOf(msg));
                    Log.e(MainActivity.class.getName(), "I got a "+Game.getOtherMove().toString());
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


}
