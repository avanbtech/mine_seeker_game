package com.example.faranak.mine_seeker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class OptionUI extends AppCompatActivity {

    Options options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_ui);
        Intent intent = getIntent();
        options = (Options) intent.getSerializableExtra("options");
        setNumOfMineOptions();
        setSizeOfGame();
        displayDefaultNumOfMine();
        displayDefaultSizeOfGame();
        makeOkButton();
        makeCancelButton();
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
        int numOfMine = 0;
        SeekBar seekbar = (SeekBar) findViewById(R.id.seekBarNumberOfMines);
        int position = seekbar.getProgress();
        switch (position){
            case 0:
                numOfMine = 6;
                break;
            case 1:
                numOfMine = 10;
                break;
            case 2:
                numOfMine = 15;
                break;
            case 3:
                numOfMine = 20;
        }
        options.setNumberOfMines(numOfMine);
    }

    private void setSizeOfGame(){
        SeekBar seekbar = (SeekBar) findViewById(R.id.seekBarGameSize);
        int position = seekbar.getProgress();
        int numOfRow = 0;
        int numOfColumn = 0;
        switch (position){
            case 0:
                numOfRow = 4;
                numOfColumn = 6;
                break;
            case 1:
                numOfRow = 5;
                numOfColumn = 10;
                break;
            case 2:
                numOfRow = 6;
                numOfColumn = 15;
                break;
        }
        options.setNumberOfRows(numOfRow);
        options.setNumberOfColumns(numOfColumn);
    }

    private void displayDefaultNumOfMine() {
        int numOfMine = options.getNumberOfMines();
        int position = 0;
        switch (numOfMine){
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
        int numOfColumn = options.getNumberOfColumns();
        int numOfRow = options.getNumberOfRows();
        int position = 0;
        switch (numOfRow){
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
