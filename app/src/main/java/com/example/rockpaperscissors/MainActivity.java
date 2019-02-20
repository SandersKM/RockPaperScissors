package com.example.rockpaperscissors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button invite;
    TextView other_IP, other_port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findIDs();
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forwardIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(forwardIntent);
            }
        });
    }

    public void findIDs(){
        setContentView(R.layout.activity_main);
        invite = findViewById(R.id.request_button);
        other_IP = findViewById(R.id.IP_textbox);
        other_port = findViewById(R.id.Port_textbox);
    }

    // TODO overall stats and stats by player
    // TODO have a database of players in the network
    // TODO have a queue of games.
}
