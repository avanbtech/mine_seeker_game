package com.example.faranak.mine_seeker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Vibrator;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.faranak.mine_seeker.mine_seeker_model.Cell;
import com.example.faranak.mine_seeker.mine_seeker_model.Gameboard;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

public class GameboardUI extends AppCompatActivity{

    private int numberOfRow;
    private int numberOfColumn;
    private int numberOfMine;
    private Gameboard gameboard;
    private Cell[][] cells;
    private SharedPreferences sharedPreferences;
    private String TOP_SCORE = "TopScore";
    private String NUMBER_PLAYED = "NumberPlayed";
    private int topScore;
    private int numberPlayed;
    private String GAME_FILE_NAME = "GameState";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gameboard_ui);
        Intent intent = getIntent();
        Options options = (Options) intent.getSerializableExtra("options");
        numberOfRow = options.getNumberOfRows();
        numberOfColumn = options.getNumberOfColumns();
        numberOfMine = options.getNumberOfMines();
        boolean loadGame = intent.getBooleanExtra("loadGame", false);
        gameboard = new Gameboard(numberOfMine, numberOfRow, numberOfColumn);
        if(loadGame) {
            loadLastGame();
            numberOfRow = gameboard.getNumberOfRow();
            numberOfColumn = gameboard.getNumberOfColumn();
            numberOfMine = gameboard.getNumberOfMine();
        }
        cells = gameboard.getBoardCells();
        createTable();
        displayTopScore();
        displayNumberOfFoundMine();
        dispalyNumberOfScan();
        updateGameboard();
    }

    private void updateGameboard() {
        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_gameboard_ui);
        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                for(int i = 0; i < numberOfRow; i++) {
                    for (int j = 0; j < numberOfColumn; j++) {
                        Button button = (Button) findViewById(i * numberOfColumn + j);
                        if (cells[i][j].getContainMine() && cells[i][j].getCellClicked()) {
                            displayMine(button);
                        }
                        else {
                            displayBackgroundButton(button);
                        }
                    }
                }
            }
        });
    }

    private void displayBackgroundButton(final Button btn){
        int newWidth = btn.getWidth();
        int newHeight = btn.getHeight();
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.minionlock);
        Bitmap scaleBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
        Resources resource = getResources();
        btn.setBackground(new BitmapDrawable(resource, scaleBitmap));

    }

    private void loadLastGame() {
        try {
            FileInputStream fis = getApplicationContext().openFileInput(GAME_FILE_NAME);
            ObjectInputStream is = new ObjectInputStream(fis);
            Gameboard loadedGameBoard = (Gameboard) is.readObject();
            is.close();
            fis.close();
            gameboard = loadedGameBoard;
        }
        catch(Exception e) {
        }
    }

    private void saveGame() {
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput(GAME_FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(gameboard);
            os.close();
            fos.close();
        }
        catch(Exception e) {
        }
    }

    private String getTopScoreConfigurationName() {
        return TOP_SCORE + numberOfMine + "_" + numberOfRow + "_" + numberOfColumn;
    }

    private String getNumberPlayedConfigurationName() {
        return NUMBER_PLAYED + numberOfMine + "_" + numberOfRow + "_" + numberOfColumn;
    }

    private boolean isSameTopScoreConfiguration(String configuration) {
        return configuration.equals(getTopScoreConfigurationName());
    }

    private boolean isSameNumberPlayedConfiguration(String configuration) {
        return configuration.equals(getNumberPlayedConfigurationName());
    }

    private void displayTopScore() {
        sharedPreferences = getApplicationContext().getSharedPreferences("TopScore", MODE_PRIVATE);
        Map<String, ?> values = sharedPreferences.getAll();
        topScore = -1;
        numberPlayed = 0;
        for (Map.Entry<String, ?> entry : values.entrySet()) {
            if(isSameTopScoreConfiguration(entry.getKey()))
            {
                topScore = sharedPreferences.getInt(entry.getKey(), 0);
            }
            else if(isSameNumberPlayedConfiguration(entry.getKey()))
            {
                numberPlayed = sharedPreferences.getInt(entry.getKey(), 0);
            }
        }
        TextView topScoreTextView = (TextView)findViewById(R.id.tvTopScore);
        if(topScore > -1) {
            topScoreTextView.setText("Top Score: " + topScore);
        }
        else{
            topScoreTextView.setText("No Top Score Yet");
        }

        TextView numberPlayedTextView = (TextView)findViewById(R.id.tvNumberPlayed);
        numberPlayedTextView.setText("Number of Games: " + numberPlayed);
    }

    private void createTable() {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.gameTable);
        TableRow tableRow = new TableRow(this);
        int counter = 0;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tableLayout.getLayoutParams();
        int topMargin = lp.topMargin;
        height -= topMargin + lp.bottomMargin;
        int buttonSpace = 20;
        int widthButtonSpace = 10;
        int totalSpaceX = (numberOfColumn + 1)*widthButtonSpace;
        int totalSpaceY = (numberOfRow + 1)*buttonSpace;
        int buttonWidth = (width - totalSpaceX) / numberOfColumn;
        int buttonHeight = (height - totalSpaceY) / numberOfRow;

        for(int i = 0; i < numberOfRow; i++) {
            tableRow = new TableRow(this);
            tableLayout.addView(tableRow);
            for(int j = 0; j < numberOfColumn; j++) {
                Button btn = new Button(this);
                btn.setLayoutParams(new TableRow.LayoutParams(buttonWidth, buttonHeight));
                btn.setId(counter);
                counter++;
                final int x = i;
                final int y = j;
                final int btnId = counter;
                displayCellContent(btn, i, j);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(gameboard.isEndOfTheGame()) {
                            return;
                        }
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
                        saveGame();
                        if (gameboard.isEndOfTheGame()) {
                            FragmentManager manager = getSupportFragmentManager();
                            GameResultAlert dialog = new GameResultAlert();
                            dialog.show(manager, "MessageGameResult");
                            saveResults();
                        }
                    }
                });
                tableRow.addView(btn);
            }
        }
    }

    private void displayMine(final Button btn) {
        int newWidth = btn.getWidth();
        int newHeight = btn.getHeight();
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(1000);
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mine_icon_scaled_bg_removed);
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

    private void saveResults(){
        int numOfScan = gameboard.getNumberOfScan();
        numberPlayed++;
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        if(topScore == -1 || topScore > numOfScan) {
            sharedPreferencesEditor.putInt(getTopScoreConfigurationName(), numOfScan);
        }
        sharedPreferencesEditor.putInt(getNumberPlayedConfigurationName(), numberPlayed);
        sharedPreferencesEditor.commit();
    }
}
