package com.example.faranak.mine_seeker;

import java.io.Serializable;

public class Options implements Serializable{
    private int numberOfMines = 8;
    private int numberOfRows = 5;
    private int numberOfColumns = 7;

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public void setNumberOfMines(int numberOfMines) {
        this.numberOfMines = numberOfMines;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }
}
