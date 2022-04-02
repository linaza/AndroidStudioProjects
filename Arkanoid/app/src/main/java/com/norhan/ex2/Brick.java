package com.norhan.ex2;

public class Brick {
    public int left,top,right,bottom;
    public boolean isVisible = true;
    int shoora;
    int aamuda;


    public Brick(){
        left=0;
        top=0;
        right=0;
        bottom=0;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public void unvisible() {
        isVisible = false;
    }
}