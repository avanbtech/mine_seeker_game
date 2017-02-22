package com.example.faranak.mine_seeker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.faranak.mine_seeker.mine_seeker_model.Cell;
import com.example.faranak.mine_seeker.mine_seeker_model.Gameboard;

import org.w3c.dom.Text;

public class GameboardUI extends AppCompatActivity {

    private int numberOfRow = 5;
    private int numberOfColumn = 7;
    private int numberOfMine = 8;
    Gameboard gameboard = new Gameboard(numberOfMine);
    Cell[][] cells = gameboard.getBoardCells();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameboard_ui);
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
//                final Button button = btn;
                displayCellContent(btn, i, j);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gameboard.getClickedLocation(new Point(x, y));
       //                 if (!cells[x][y].getCellClicked()){
                            displayNumberOfFoundMine();
                            dispalyNumberOfScan();
       //                 }
                        for (int i = 0; i < numberOfColumn; i++){
                            Button button = (Button) findViewById(x * numberOfColumn + i);
                            displayCellContent(button,x, i);
                        }
                        for (int j = 0; j < numberOfRow; j++){
                            Button button = (Button) findViewById(j * numberOfColumn + y);
                            displayCellContent(button, j, y);
                        }
                    }
                });
                tableRow.addView(btn);
            }
        }
    }

    private void displayCellContent(Button btn, int x, int y){
        if (cells[x][y].getContainMine() && cells[x][y].getCellClicked()){
            int newWidth = btn.getWidth();
            int newHeight = btn.getHeight();
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
            Bitmap scaleBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
            Resources resource = getResources();
            btn.setBackground(new BitmapDrawable(resource, scaleBitmap));
           // btn.setBackgroundResource(R.drawable.ball);
            if (cells[x][y].getCellNumShown()){
                btn.setText("" + cells[x][y].getCellNumber());
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
