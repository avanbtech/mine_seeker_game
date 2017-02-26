package com.example.faranak.mine_seeker;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.faranak.mine_seeker.mine_seeker_model.Gameboard;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MineSeeker extends AppCompatActivity {

    Options options;
    String OPTIONS_FILE_NAME = "options";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mine_seeker);
        makePlayGameButton();
        makeLoadGameButton();
        makeOptionButton();
        makeHelpButton();
        loadOptions();
    }

    private void loadOptions() {
        options = new Options();
        try {
            FileInputStream fis = getApplicationContext().openFileInput(OPTIONS_FILE_NAME);
            ObjectInputStream is = new ObjectInputStream(fis);
            options = (Options) is.readObject();
            is.close();
            fis.close();
        }
        catch(Exception e) {
        }
    }

    private void saveOptions() {
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput(OPTIONS_FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(options);
            os.close();
            fos.close();
        }
        catch(Exception e) {
        }
    }

    private void makePlayGameButton() {
        Button btnPalyGame = (Button) findViewById(R.id.btnPlayGame);
        btnPalyGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MineSeeker.this, GameboardUI.class);
                intent.putExtra("options", options);
                intent.putExtra("loadGame", false);
                startActivity(intent);
            }
        });
    }

    private void makeLoadGameButton() {
        Button btnLoadGame = (Button) findViewById(R.id.btnLoadGame);
        btnLoadGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MineSeeker.this, GameboardUI.class);
                intent.putExtra("options", options);
                intent.putExtra("loadGame", true);
                startActivity(intent);
            }
        });
    }

    private void makeOptionButton() {
        Button btnOption = (Button) findViewById(R.id.btnOption);
        btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        if (requestCode == 50 && resultCode == RESULT_OK){
            options = (Options) data.getSerializableExtra("options");
            saveOptions();
        }
    }
}
