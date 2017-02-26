package com.example.faranak.mine_seeker.mine_seeker_model;

import android.graphics.Point;

import java.io.Serializable;

/**
 * Created by faranakpouya on 2017-02-11.
 */

public class Cell implements Serializable{

    boolean cellClicked = false;
    boolean containMine = false;
    int cellNumber = 0;
    boolean cellNumShown = false;

    public int getCellNumber(){
        return cellNumber;
    }

    public void setCellNumber(int cellNumber){
        this.cellNumber = cellNumber;
    }

    public void setCellClicked(boolean clicked){
        this.cellClicked = clicked;
    }

    public boolean getCellClicked(){
        return cellClicked;
    }

    public boolean getContainMine(){
        return containMine;
    }

    public void setContainMine(boolean containMine){
        this.containMine = containMine;
    }

    public void setCellNumShown(boolean cellNumShown){
        this.cellNumShown = cellNumShown;
    }

    public boolean getCellNumShown(){
        return cellNumShown;
    }
}
