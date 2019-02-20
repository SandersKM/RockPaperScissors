package com.example.rockpaperscissors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button invite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button invite = findViewById(R.id.request_button);
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forwardIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(forwardIntent);
            }
        });
    }

    // TODO overall stats and stats by player
    // TODO have a database of players in the network
    // TODO have a queue of games.
}
