package com.example.faranak.mine_seeker;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.faranak.mine_seeker.mine_seeker_model.Gameboard;

public class MineSeeker extends AppCompatActivity {

    Options options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_seeker);
        makePlayGameButton();
        makeOptionButton();
        makeHelpButton();
        options = new Options();
    }

    private void makePlayGameButton() {
        Button btnPalyGame = (Button) findViewById(R.id.btnPlayGame);
        btnPalyGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MineSeeker.this, GameboardUI.class);
                intent.putExtra("options", options);
                startActivity(intent);
            }
        });
    }

    private void makeOptionButton() {
        Button btnOption = (Button) findViewById(R.id.btnOption);
        btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Options options = new Options();
                Intent intent = new Intent(MineSeeker.this, OptionUI.class );
                intent.putExtra("options", options);
                startActivityForResult(intent, 50);
            }
        });
    }

    private void makeHelpButton() {
        Button btnHelp = (Button) findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MineSeeker.this, HelpUI.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 50 && requestCode == RESULT_OK){
            options = (Options) data.getSerializableExtra("options");
        }
    }
}
