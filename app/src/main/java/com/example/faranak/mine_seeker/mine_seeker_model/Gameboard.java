package com.example.faranak.mine_seeker.mine_seeker_model;

import android.graphics.Point;

import java.util.Random;

/**
 * Created by faranakpouya on 2017-02-11.
 */

public class Gameboard {
    private int numberOfRow = 5;
    private int numberOfColumn = 7;
    private int numberOfFound = 0;
    private int numberOfScan = 0;
    private int numberOfMine; //should get from UI
    private Cell[][] boardCells = new Cell[numberOfRow][numberOfColumn];

    public Cell[][] getBoardCells(){
        return boardCells;
    }

    public int getNumberOfMine(){
        return numberOfMine;
    }

    public int getNumberOfFound(){
        return numberOfFound;
    }

    public int getNumberOfScan(){
        return numberOfScan;
    }

    public Gameboard(int numberOfMine){
        this.numberOfMine = numberOfMine;
        boolean endGame = false;
        for (int i = 0; i < numberOfRow; i++){
            for (int j = 0; j < numberOfColumn; j++){
                boardCells[i][j] = new Cell();
            }
        }
        randomLocations(numberOfMine);
        for(int i = 0; i < numberOfRow; i++){
            for(int j = 0; j < numberOfColumn; j++){
                boardCells[i][j].setCellClicked(false);
                calculateCellNumber(new Point(i, j));
            }
        }
        //Need to use random locations for mine
    }

    public void setNumberOfRow(int numberOfRow){
        this.numberOfRow = numberOfRow;
    }
    public int getNumberOfRow(){
        return numberOfRow;
    }
    public int getNumberOfColumn(){
        return numberOfColumn;
    }
    public void setNumberOfColumn(int numberOfColumn){
        this.numberOfColumn = numberOfColumn;
    }
    private void randomLocations(int numberOfMine){
        //should check of repitation
        Random r = new Random();
        int x, y;
        for (int i = 0; i < numberOfMine; i++) {
            x = Math.abs(r.nextInt()) % numberOfRow;
            y = Math.abs(r.nextInt()) % numberOfColumn;
            boardCells[x][y].setContainMine(true);
        }
    }
    public void calculateCellNumber(Point location){    //Should be checked and check for update and clicked cell
        int number = 0;
        for(int i = 0; i < numberOfColumn; i++){
            if(boardCells[location.x][i].getContainMine() && !boardCells[location.x][i].getCellClicked()) {
                number++;
            }
        }
        for(int j = 0; j < numberOfRow; j++){
            if(boardCells[j][location.y].getContainMine() && !boardCells[j][location.y].getCellClicked()) {
                number++;
            }
        }
        if (boardCells[location.x][location.y].getContainMine()){
            boardCells[location.x][location.y].setCellNumber(number - 1);
        }
        else {
            boardCells[location.x][location.y].setCellNumber(number);
        }
    }

    public void getClickedLocation(Point location){
        if (validateClick(location)){
            updateGameboard(location);
        }
    }
    private void updateCellStatus(Point location){
        if (!boardCells[location.x][location.y].getContainMine()) {
            boardCells[location.x][location.y].setCellNumShown(true);
        }
        else if (boardCells[location.x][location.y].getCellClicked()){
            boardCells[location.x][location.y].setCellNumShown(true);
        }
        boardCells[location.x][location.y].setCellClicked(true);
    }
    private void updateNumberOfFound(Point location){
        if(boardCells[location.x][location.y].getContainMine()&& !boardCells[location.x][location.y].getCellNumShown()){
            numberOfFound++;
        }
    }

    private boolean isEndOfTheGame(){
        return (numberOfMine == numberOfFound);
    }

    public void updateGameboard(Point location){
        if (isEndOfTheGame()){
            //Display cong. reset eveything
            return;
        }
        //numberOfScan should be checked again for shorter code
        if(!boardCells[location.x][location.y].getCellClicked() && boardCells[location.x][location.y].getContainMine()){
            numberOfScan--;
        }
        numberOfScan++;
        updateCellNumbersInRowAndCol(location);
        updateCellStatus(location);
        updateNumberOfFound(location);
    }

    private void updateCellNumbersInRowAndCol(Point location){
        if(boardCells[location.x][location.y].getContainMine() && !boardCells[location.x][location.y].getCellNumShown()
                && boardCells[location.x][location.y].getCellClicked()){
            return;
        }
        if(boardCells[location.x][location.y].getContainMine()){
            for (int i = 0; i < numberOfColumn; i++) {
                int cellNum = boardCells[location.x][i].getCellNumber();
                if (i != location.y && cellNum > 0) {
                    boardCells[location.x][i].setCellNumber(cellNum - 1);
                }
            }
            for(int j = 0; j < numberOfRow; j++) {
                int cellNum = boardCells[j][location.y].getCellNumber();
                if (j != location.x && cellNum > 0) {
                    boardCells[j][location.y].setCellNumber(cellNum - 1);
                }
            }
            int cellNum = boardCells[location.x][location.y].getCellNumber();
            if(cellNum > 0) {
                boardCells[location.x][location.y].setCellNumber(cellNum - 1);
            }
        }
    }

    public boolean validateClick(Point location){
        if (!boardCells[location.x][location.y].getCellNumShown()){
            return true;
        }
        return false;
    }
}