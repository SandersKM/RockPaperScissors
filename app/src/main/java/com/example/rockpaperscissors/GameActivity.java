package com.example.rockpaperscissors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {

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

    public void moveListener(){
        rockListener();
        paperListener();
        scissorsListener();
    }

    // Depending on what needs to be sent, we could make these into one generic method,
    // but I figured we should keep it "simpler" for now by having separate methods
    public void rockListener(){
        rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void paperListener(){
        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void scissorsListener(){
        scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    // I'm just gonna throw down some pseudocode here until the ENUMs are finalized
    // I think because we are running this locally, then keeping the comparisons between
    //   *me* and *other* for the tie/loss/win conditions it works out perfectly. Once I get
    //   the ENUMs down properly I can see about testing and making compareMoves() actually work.
    public void compareMoves() {
        // me.rock-other.rock || me.paper-other.paper || me.scissors-other.scissors --> popup tie
        // me.rock-other.paper || me.paper-other.scissors || me.scissors-other.rock --> popup loss
        // me.rock-other.scissors || me.paper-other.rock || me.scissors-other.paper --> popup win

        // Display the quit/play again screen? or just call playAgain()?

        
    }
}
