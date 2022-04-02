package com.norhan.ex2;

public class Paddle {
    private float xcPaddle;
    private float x_c_paddle;
    private float y_c_paddle;
    private float paddle_left;
    private float paddle_right;

    public float getPaddle_right() {
        return paddle_right;
    }

    public void setPaddle_right(float paddle_right) {
        this.paddle_right = paddle_right;
    }


    public float getPaddle_left() {
        return paddle_left;
    }

    public void setPaddle_left(float paddle_left) {
        this.paddle_left = paddle_left;
    }

    public float getX_c_paddle() {
        return x_c_paddle;
    }

    public void setX_c_paddle(float x_c_paddle) {
        this.x_c_paddle = x_c_paddle;
    }

    public float getY_c_paddle() {
        return y_c_paddle;
    }

    public void setY_c_paddle(float y_c_paddle) {
        this.y_c_paddle = y_c_paddle;
    }

    public float getXcPaddle() {
        return xcPaddle;
    }

    public void setXcPaddle(float xcPaddle) {
        this.xcPaddle = xcPaddle;
    }
}