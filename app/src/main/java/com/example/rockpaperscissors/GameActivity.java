package com.example.rockpaperscissors;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.net.Socket;

public class GameActivity extends AppCompatActivity {

    final Context context = this;
    Button rock, paper, scissors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findIDs();
        moveListener();
    }

    public void findIDs(){
        setContentView(R.layout.activity_game);
        rock = findViewById(R.id.stone_button);
        paper = findViewById(R.id.paper_button);
        scissors = findViewById(R.id.scissors_button);
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
              //  send(Move.Rock.toString(),,8888);
                showResult(getResult());
            }
        });
    }



    public void paperListener(){
        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           //     send(Move.Paper.toString(),,8888);
                showResult(getResult());
            }
        });
    }

    public void scissorsListener(){
        scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    send(Move.Scissor.toString(),,8888);
                showResult(getResult());
            }
        });
    }

    public void send(final String message, final String host, final int port) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Socket target = new Socket(host, port);
                    Communication.sendOver(target, message);
                    target.close();
                } catch (final Exception e) {
                 /*   MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Utilities.notifyException(MainActivity.this, e);
                        }
                    });*/
                }

            }
        }.start();
    }



}
