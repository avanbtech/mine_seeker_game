package com.example.faranak.mine_seeker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class OptionUI extends AppCompatActivity {

    private Options options;
    private int numberOfRow;
    private int numberOfColumn;
    private int numberOfMines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_option_ui);
        Intent intent = getIntent();
        options = (Options) intent.getSerializableExtra("options");
        displayDefaultNumOfMine();
        displayDefaultSizeOfGame();
        setNumOfMineOptions();
        setSizeOfGame();
        makeOkButton();
        makeCancelButton();
        displaySizeOfGame();
        displayNumberOfMines();
        makeNumberOfMineSeekbar();
        makeGameSizeSeekbar();
    }

    private void makeNumberOfMineSeekbar() {
        SeekBar seekbar = (SeekBar) findViewById(R.id.seekBarNumberOfMines);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setNumOfMineOptions();
                displayNumberOfMines();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void makeGameSizeSeekbar() {
        SeekBar seekbar = (SeekBar) findViewById(R.id.seekBarGameSize);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setSizeOfGame();
                displaySizeOfGame();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    private void displaySizeOfGame() {
        TextView tvSizeOfGame = (TextView) findViewById(R.id.tvGameSize);
        tvSizeOfGame.setText("Game Size: " + numberOfRow + "x" + numberOfColumn);
    }

    private void displayNumberOfMines() {
        TextView tvNumberOfMine = (TextView) findViewById(R.id.tvNumberOfMines);
        tvNumberOfMine.setText("Number of Mines: " + numberOfMines);
    }

    private void makeOkButton() {
        Button btnOK = (Button) findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("options", options);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void makeCancelButton() {
        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private void setNumOfMineOptions() {
        SeekBar seekbar = (SeekBar) findViewById(R.id.seekBarNumberOfMines);
        int position = seekbar.getProgress();
        switch (position){
            case 0:
                numberOfMines = 6;
                break;
            case 1:
                numberOfMines = 10;
                break;
            case 2:
                numberOfMines = 15;
                break;
            case 3:
                numberOfMines = 20;
        }
        options.setNumberOfMines(numberOfMines);
    }

    private void setSizeOfGame(){
        SeekBar seekbar = (SeekBar) findViewById(R.id.seekBarGameSize);
        int position = seekbar.getProgress();
        switch (position){
            case 0:
                numberOfRow = 4;
                numberOfColumn = 6;
                break;
            case 1:
                numberOfRow = 5;
                numberOfColumn = 10;
                break;
            case 2:
                numberOfRow = 6;
                numberOfColumn = 15;
                break;
        }
        options.setNumberOfRows(numberOfRow);
        options.setNumberOfColumns(numberOfColumn);
    }

    private void displayDefaultNumOfMine() {
        numberOfMines = options.getNumberOfMines();
        int position = 0;
        switch (numberOfMines){
            case 6:
                position = 0;
                break;
            case 10:
                position = 1;
                break;
            case 15:
                position = 2;
                break;
            case 20:
                position = 3;
                break;
        }
        SeekBar seeker = (SeekBar) findViewById(R.id.seekBarNumberOfMines);
        seeker.setProgress(position);
    }

    private void displayDefaultSizeOfGame() {
        numberOfColumn = options.getNumberOfColumns();
        numberOfRow = options.getNumberOfRows();
        int position = 0;
        switch (numberOfRow){
            case 4:
                position = 0;
                break;
            case 5:
                position = 1;
                break;
            case 6:
                position = 2;
                break;
        }
        SeekBar seeker = (SeekBar) findViewById(R.id.seekBarGameSize);
        seeker.setProgress(position);
    }
}
