package com.example.faranak.mine_seeker;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Vibrator;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.faranak.mine_seeker.mine_seeker_model.Cell;
import com.example.faranak.mine_seeker.mine_seeker_model.Gameboard;

import org.w3c.dom.Text;

public class GameboardUI extends AppCompatActivity {

    private int numberOfRow;
    private int numberOfColumn;
    private int numberOfMine;
    Gameboard gameboard;
    Cell[][] cells;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameboard_ui);
        Intent intent = getIntent();
        Options options = (Options) intent.getSerializableExtra("options");
        numberOfRow = options.getNumberOfRows();
        numberOfColumn = options.getNumberOfColumns();
        numberOfMine = options.getNumberOfMines();
        gameboard = new Gameboard(numberOfMine, numberOfRow, numberOfColumn);
        cells = gameboard.getBoardCells();
        createTable();
    }

    private void createTable() {
        TableLayout myLayout = (TableLayout) findViewById(R.id.gameTable);
        TableRow tableRow = new TableRow(this);
        int counter = 0;
        for(int i = 0; i < numberOfRow; i++) {
            tableRow = new TableRow(this);
            myLayout.addView(tableRow);
            for(int j = 0; j < numberOfColumn; j++) {
                Button btn = new Button(this);
                btn.setId(counter);
                counter++;
                final int x = i;
                final int y = j;
                final int btnId = counter;
                displayCellContent(btn, i, j);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cells[x][y].getContainMine() && !cells[x][y].getCellClicked()) {
                            Button button = (Button) findViewById(x * numberOfColumn + y);
                            displayMine(button);
                        }
                        gameboard.getClickedLocation(new Point(x, y));
                            displayNumberOfFoundMine();
                            dispalyNumberOfScan();
                        for (int i = 0; i < numberOfColumn; i++){
                            Button button = (Button) findViewById(x * numberOfColumn + i);
                            displayCellContent(button,x, i);
                        }
                        for (int j = 0; j < numberOfRow; j++){
                            Button button = (Button) findViewById(j * numberOfColumn + y);
                            displayCellContent(button, j, y);
                        }
                        if (gameboard.isEndOfTheGame()) {
                            FragmentManager manager = getSupportFragmentManager();
                            GameResultAlert dialog = new GameResultAlert();
                            dialog.show(manager, "MessageGameResult");
                        }
                    }
                });
                tableRow.addView(btn);
            }
        }
    }

    private void displayMine(final Button btn) {
        final int newWidth = btn.getWidth();
        final int newHeight = btn.getHeight();
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        //Animation animation = new TranslateAnimation(-newWidth, 0, 0, 0);
        animation.setDuration(1000);
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mine_icon_scaled);
        Bitmap scaleBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
        Resources resource = getResources();
        btn.setAnimation(animation);
        btn.setBackground(new BitmapDrawable(resource, scaleBitmap));
        btn.startAnimation(animation);
        try {
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);
        }
        catch (Exception e){

        }
    }

    private void displayCellContent(Button btn, int x, int y){
        if (cells[x][y].getContainMine() && cells[x][y].getCellClicked()){
            if (cells[x][y].getCellNumShown()){
                btn.setText("" + cells[x][y].getCellNumber());
            }
            else{
                btn.setText("?");
            }
        }
        else if(!cells[x][y].getContainMine() && cells[x][y].getCellClicked()){
            btn.setText("" + cells[x][y].getCellNumber());
        }
    }

    private void displayNumberOfFoundMine(){
        int numOfFound = gameboard.getNumberOfFound();
        String updateText = "Found " + numOfFound + " From " + gameboard.getNumberOfMine();
        TextView mineText = (TextView) findViewById(R.id.mineFound);
        mineText.setText(updateText);
    }

    private void dispalyNumberOfScan(){
        int numOfScan = gameboard.getNumberOfScan();
        String updateScanText = "# Scan used: " + gameboard.getNumberOfScan();
        TextView scanText = (TextView) findViewById(R.id.ScanUsed);
        scanText.setText(updateScanText);
    }
}
