package com.example.faranak.mine_seeker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MineSeeker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_seeker);

        makePlayGameButton();
    }

    private void makePlayGameButton() {
        Button btnPalyGame = (Button) findViewById(R.id.btnPlayGame);
        btnPalyGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MineSeeker.this, GameboardUI.class);
                startActivity(intent);
            }
        });
    }
}
