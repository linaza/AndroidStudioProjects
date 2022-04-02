package com.norhan.ex2;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import java.util.Arrays;

public class GameView extends View
{
    private SensorManager senManager;
    private Sensor sen;
    private SensorEventListener sensorListener;
    private Ball ball;
    private Brick brick;
    private Paddle paddle;
    private float Paddle_x;
    private Paint ball_pen, brick_pen, paddle_pen;
    private int canvasW, canvasH;
    public int ROWS = 5;
    public int COLS = 4;
    private int startTop;
    int psteps, pleft, pright;
    int ball_speed =15;
    float ball_speed_x=15;
    float ball_speed_y=-15;
    private int limit_arr[];
    Boolean startgame = false;
    Boolean hit_brick=false;
    Boolean ball_hit_paddle=false;
    int row_limet [];
    int first_move=0;
    BrickCollection brickList;
    int count=0;

    String startScreen ="Click To Play";

    private int score=0;
    private int lives=3;

    Canvas canvas;
    //----------------------------------------------------------------------------------------------
    public GameView(Context context,AttributeSet attrs) {

        super(context, attrs);
        senManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        sen = senManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                senManager.registerListener(this, sen, SensorManager.SENSOR_DELAY_NORMAL);
                onSensorChanged(event);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

        };
        ball = new Ball();
        paddle=new Paddle();
        brick=new Brick();
        brickList = new BrickCollection(ROWS,COLS);
        this.limit_arr=new int[COLS];
        Arrays.fill(this.limit_arr,ROWS);
        ball_pen = new Paint();
        ball_pen.setColor(Color.WHITE);
        ball_pen.setStyle(Paint.Style.STROKE);
        ball_pen.setStyle(Paint.Style.FILL);
        brick_pen = new Paint();
        brick_pen.setColor(Color.RED);
        brick_pen.setStyle(Paint.Style.FILL);
        paddle_pen = new Paint();
        paddle_pen.setColor(Color.BLUE);
        paddle_pen.setStyle(Paint.Style.FILL);
        paddle_pen.setStrokeWidth(20);
    }

    // callback onSensorChanged
    public void onSensorChanged(SensorEvent event)
    {
        switch (event.sensor.getType())
        {
            case Sensor. TYPE_ACCELEROMETER :
                float x = event.values[0]; // Acceleration force along the x axis
                this.paddle.setPaddle_right(x);
                this.paddle.setPaddle_left(this.paddle.getX_c_paddle() - psteps / 2);
                canvas.drawRect(this.paddle.getPaddle_left(),canvasH-50, this.paddle.getPaddle_right(), canvasH -10, paddle_pen);
                // float y = event.values[1]; // Acceleration force along the y axis
               // float z = event.values[2]; // Acceleration force along the z axis
                break;
        }
    }
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);
        draw_Bricks(canvas);
        openingText(canvas);
        draw_paddle(canvas);
        draw_ball(canvas);

        scoreText(canvas);
        livesText(canvas);

        if (startgame) {
            move_ball();
            postInvalidate();
            check_boundres();
            check_ball_inter_paddle();
            check_ball_intersect_Brick();
            first_move = 1;
        }

/////////////////////this will update the circle
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                    check_ball_intersect_Brick();
//
//
//            }
//        }).start();
   }



    boolean ballhitOnground = false;

    private void check_boundres(){


        if(ball.getYball()-ball.getRadios()<=0 ){

            ball_speed=ball_speed*-1;
//            this.ball_speed_x*=-1;
            this.ball_speed_y*=-1;

        }
        if(ball.getXball()-ball.getRadios()<=0||ball.getXball()+ball.getRadios()>=this.canvasW){

            this.ball_speed_x*=-1;


        }


        if(ball.getYball()+ball.getRadios()>=this.canvasH){

           if(flag){
            //   Log.d("GameViewBoundries", "Ball Do Not Hit the paddle true" );
               pauseGame();
               ballhitOnground  = true;
           }else{
             //  Log.d("GameViewBoundries", "Ball Hit paddle true" );
               ballhitOnground  = false;
           }




        }
    }
    //--------------------------------------------------------------------------------------------------
    boolean flag= true;



    private void move_ball() {



            if (this.ball_hit_paddle == true) {

                this.ball_hit_paddle = false;
                this.ball_speed_x = (this.ball.getXball() - this.paddle.getX_c_paddle()) / 10;
                this.ball_speed_y *= -1;
                this.ball.setYball(canvasH - 60);
                // this.count++;
                // Log.d("MULTIPLE", "speed bal y is "+this.ball_speed_y+"paddele center  is sss "+this.ball_speed_x + "count "+count);

            } else {

                if (first_move == 1) {

                    this.ball.setXball(this.ball.getXball());


                }

                this.ball.setXball((this.ball.getXball() + ball_speed_x));
                this.ball.setYball(this.ball.getYball() + ball_speed_y);

            }


    }
    //--------------------------------------------------------------------------------------------------
    private void draw_ball(Canvas canvas) {
        canvas.drawCircle(ball.getXball(),ball.getYball(),ball.getRadios(),ball_pen);
    }
    //--------------------------------------------------------------------------------------------------



    private void check_ball_inter_paddle(){

        if((this.ball.getYball()+this.ball.getRadios())>(canvasH-5)) {
            if (this.ball.getXball() > this.paddle.getPaddle_left() && this.ball.getXball() < this.paddle.getPaddle_right())
                this.ball_hit_paddle = true;
                flag=true;
        }else{
            flag=false;
        }
        /*    Log.d("GameViewBoundries", "Ball Hit paddle" );
        }else{

            Log.d("GameViewBoundries", "do not hit paddle" );

        }*/

    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(startScreen.equals("Click To Play")){
            startScreen ="";
        }else if (startScreen.equals("Play Again")){
            startScreen ="";


            resumeGame();

        }else if (startScreen.equals("You Lost. Play Again")){

        startScreen ="";



            lives = 3;

            resumeGame();

        }else if(startScreen.equals("You Win Click To Replay")){
            startScreen ="";

          drawBricks();

          resumeGame();

        }


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.paddle.setX_c_paddle(event.getX());
                this.paddle.setPaddle_left((this.paddle.getX_c_paddle()-psteps/2));
                this.paddle.setPaddle_right((this.paddle.getX_c_paddle()+psteps/2));
                float y =event.getY();
        }
        Log.d("MULTIPLE", "X=" + Paddle_x + " Y=" + event.getY());
        this.startgame=true;
        postInvalidate();
        return true;
    }

    private void resumeGame() {
        ball_speed =15;
        ball_speed_x=15;
        ball_speed_y=-15;


        move_ball();
    }

    private void draw_paddle(Canvas canvas) {
        canvas.drawRect(this.paddle.getPaddle_left(),canvasH-50, this.paddle.getPaddle_right(), canvasH -10, paddle_pen);
    }


    private void  draw_Bricks(Canvas canvas){
        int temptop=startTop;
        int steps =canvasH/18;
        int left=10;
        int leftsteps=canvasW/(COLS);
        int right=left+leftsteps;
        //int tempbottom=(temptop+steps)-100;
        for(int row=0; row<ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if(brickList.brickCollection[row][col].isVisible==true)
                    canvas.drawRect(left, temptop,right,temptop+steps, brick_pen);
                temptop += steps + 20;//-----------------------
                brickList.brickCollection[row][col].setLeft(left);
                brickList.brickCollection[row][col].setRight(right);
                brickList.brickCollection[row][col].setBottom(temptop+steps);
                brickList.brickCollection[row][col].setTop(temptop);
            }
            left=(right+5);
            right=(left+leftsteps);
            temptop=startTop;
        }
    }
    public boolean win=false;


    boolean winState  = false;


    private void check_ball_intersect_Brick() {




        if(win) {
            ball.setYball(canvasH);
            ball.setXball(canvasW / 2);





            return;
        }
        if(count==16) {
            ball.setYball(canvasH);
            ball.setXball(canvasW / 2);
            Log.d("MULTIPLE", "speed bal y is " + this.ball_speed_y + "paddele center  is sss " + this.ball_speed_x + "count " + count);
            win=true;
            return;
        }

        if(score==80){
            winState  = true;
        }else{
            winState  = false;
        }

        if(winState  == false){

        }else{
            winState= false;
            pauseGame();
            startScreen = "You Win Click To Replay";
        }

        for(int row=0; row<ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if((brickList.brickCollection[row][col].isVisible && brickList.brickCollection[row][col].top<=this.ball.getYball() + this.ball.getRadios()&& brickList.brickCollection[row][col].bottom >= this.ball.getYball() + this.ball.getRadios()) ) {
                    if (brickList.brickCollection[row][col].isVisible && brickList.brickCollection[row][col].left <= this.ball.getXball() + this.ball.getRadios() && brickList.brickCollection[row][col].right >= this.ball.getXball() + this.ball.getRadios()) {
                        brickList.brickCollection[row][col].unvisible();
                        this.ball_speed_y *= -1;
                        this.ball_speed_x*=-1;

                        score =score+5;


                      count++;

                    }

                }
            }
        }


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasW = w;
        canvasH = h;
        Log.d(getClass().getName(), String.format("value = %d", w));
        startTop=canvasH/8;
        psteps=canvasW/5;
        Paddle_x=canvasW/3;
        this.ball.setXball(canvasW/2);
        this.ball.setYball(canvasH-90);
        this.ball.setRadios(30);
        this.paddle.setX_c_paddle(canvasW/2);
        this.paddle.setY_c_paddle(canvasH-25);
        this.paddle.setXcPaddle(this.paddle.getX_c_paddle()-psteps/2);


    }

    private void scoreText(Canvas canvas){
        Paint paint = new Paint();

        paint.setColor(Color.GREEN);
        paint.setTextSize(30);
        canvas.drawText("Score: "+score, 20, 40, paint);

    }



    private void livesText(Canvas canvas){
        Paint paint = new Paint();

        paint.setColor(Color.GREEN);
        paint.setTextSize(30);
        canvas.drawText("Lives: "+lives, canvas.getWidth()-140, 40, paint);

    }

    private void openingText(Canvas canvas){

        Paint paint = new Paint();
        int xPos = (canvas.getWidth() / 2)-80;
        int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2)+20) ;

        paint.setColor(Color.GREEN);
        paint.setTextSize(40);
        canvas.drawText(startScreen, xPos, yPos, paint);

    }

    private void pauseGame(){

if(lives>1) {

    ball_speed = 0;
    ball_speed_x = 0;
    ball_speed_y = -0;
    startgame = false;
    hit_brick = false;
    ball_hit_paddle = false;
    count = 0;


    startTop = canvasH / 8;
    psteps = canvasW / 5;
    Paddle_x = canvasW / 3;
    this.ball.setXball(canvasW / 2);
    this.ball.setYball(canvasH - 90);
    this.ball.setRadios(30);
    this.paddle.setX_c_paddle(canvasW / 2);
    this.paddle.setY_c_paddle(canvasH - 25);
    this.paddle.setXcPaddle(this.paddle.getX_c_paddle() - psteps / 2);
    lives = lives - 1;


    startScreen = "Play Again";
}else{
    lives = lives - 1;
    ball_speed = 0;
    ball_speed_x = 0;
    ball_speed_y = -0;
    startgame = false;
    hit_brick = false;
    ball_hit_paddle = false;
    count = 0;


    startTop = canvasH / 8;
    psteps = canvasW / 5;
    Paddle_x = canvasW / 3;
    this.ball.setXball(canvasW / 2);
    this.ball.setYball(canvasH - 90);
    this.ball.setRadios(30);
    this.paddle.setX_c_paddle(canvasW / 2);
    this.paddle.setY_c_paddle(canvasH - 25);
    this.paddle.setXcPaddle(this.paddle.getX_c_paddle() - psteps / 2);

    score = 0;
    drawBricks();


    startScreen = "You Lost. Play Again";



}
    }


    private void drawBricks(){

        Log.d("GameViewBoundries", "Draw Brick Called " );

        ball = new Ball();
        paddle=new Paddle();
        brick=new Brick();
        brickList = new BrickCollection(ROWS,COLS);
        this.limit_arr=new int[COLS];
        Arrays.fill(this.limit_arr,ROWS);
        ball_pen = new Paint();
        ball_pen.setColor(Color.WHITE);
        ball_pen.setStyle(Paint.Style.STROKE);
        ball_pen.setStyle(Paint.Style.FILL);
        brick_pen = new Paint();
        brick_pen.setColor(Color.RED);
        brick_pen.setStyle(Paint.Style.FILL);
        paddle_pen = new Paint();
        paddle_pen.setColor(Color.BLUE);
        paddle_pen.setStyle(Paint.Style.FILL);
        paddle_pen.setStrokeWidth(20);




        startTop=canvasH/8;
        psteps=canvasW/5;
        Paddle_x=canvasW/3;
        this.ball.setXball(canvasW/2);
        this.ball.setYball(canvasH-90);
        this.ball.setRadios(30);
        this.paddle.setX_c_paddle(canvasW/2);
        this.paddle.setY_c_paddle(canvasH-25);
        this.paddle.setXcPaddle(this.paddle.getX_c_paddle()-psteps/2);

        draw_ball(canvas);
        draw_Bricks(canvas);





    }

}