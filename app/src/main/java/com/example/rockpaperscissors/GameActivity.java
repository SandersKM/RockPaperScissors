package com.example.rockpaperscissors;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {

    Button rock, paper, scissors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findIDs();
    }
    
    public void findIDs(){
        setContentView(R.layout.activity_game);
        rock = findViewById(R.id.stone_button);
        paper = findViewById(R.id.paper_button);
        scissors = findViewById(R.id.scissors_button);
    }
}
