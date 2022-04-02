package com.norhan.ex2;

public class BrickCollection {

    public int col;
    public int row;
    public Brick [][] brickCollection;
    public BrickCollection(int rows, int cols)
    {
        this.row=rows;
        this.col=cols;
        brickCollection=new Brick[row][col];
        for(int row=0; row<rows; row++) {
            for (int col = 0; col < cols; col++) {
                brickCollection[row][col]=new Brick();
            }
        }
    }

    public Brick[][] getCollection_arr() {
        return brickCollection;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Brick getBrick(int row,int col) {
        return brickCollection[row][col];
    }

    public void setBrick(Brick b, int row, int col) {
        this.brickCollection[row][col] = b;
    }
}
