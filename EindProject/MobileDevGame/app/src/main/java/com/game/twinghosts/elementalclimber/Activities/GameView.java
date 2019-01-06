package com.game.twinghosts.elementalclimber.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import com.game.twinghosts.elementalclimber.Data.GameData;
import com.game.twinghosts.elementalclimber.Data.GameManager;
import com.game.twinghosts.elementalclimber.Data.HiScore;
import com.game.twinghosts.elementalclimber.GameObjects.Obstacles.BlockObstacle;
import com.game.twinghosts.elementalclimber.GameObjects.Player;
import com.game.twinghosts.elementalclimber.GameObjects.Obstacles.BaseObstacle;
import com.game.twinghosts.elementalclimber.R;
import com.game.twinghosts.elementalclimber.Utility.Vector2;

import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private Player player;
    private Paint paint = new Paint();
    private GameManager gameManager;
    private CountDownTimer countDownTimer;
    private Vector2 screenSize;
    private boolean gameIsRunning = true;
    private boolean gameIsLost = false;
    private boolean showLostWindow = false;

    private GameActivity context;

    private boolean gamePaused = false;

    public GameView(Context context){
        super(context);

        getHolder().addCallback(this);

        GameData.hiScoreToStore = new HiScore("No Name", 0);

        this.context = (GameActivity)context;

        // Store the screen width and height
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenSize = new Vector2(size.x, size.y);

        // camera = new Camera(new Vector2(0, 0));

        GameData.floorHeight = ((int)screenSize.y / 8) * 5;
        GameData.ceilingHeight = ((int)screenSize.y / 8) * 3;

        gameManager = new GameManager(context);

        // Create the player
        player = new Player(screenSize.x/7, screenSize.y/2, new Vector2(GameData.BLOCK_SIZE, GameData.BLOCK_SIZE));
        player.setScene(this);

        // Run the game on a custom mainThread
        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        createBlockMovementCounter();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                player.switchGravity();
            }
        });
    }

    /**
     * Updates the game before the draw function happens so that all objects are in the right place
     * before drawing them.
     */
    @SuppressLint("SetTextI18n")
    public void update() {
        if(gameIsRunning) {
            if(!gamePaused) {
                GameData.hiScoreToStore.addScore(1);
                player.update(gameManager);
                player.setCollisionRectangle();
                gameManager.updateObstacles();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(canvas == null) return;
        canvas.drawColor(Color.BLACK);

        drawPlayer(canvas);
        drawFloor(canvas);
        drawObstacles(canvas);
        drawScore(canvas);

        paint.setColor(Color.BLACK);
    }

    private void createBlockMovementCounter(){
        int difficultyTimerInterval = 8;
        int interval = 1500 - GameData.DIFFICULTY_INTERVAL_REDUCTION * GameData.difficulty;
        countDownTimer = new CountDownTimer(difficultyTimerInterval * 1000, interval) {
            public void onTick(long millisUntilFinished) {
                // Score increment per tick
                if(!gamePaused) {
                    GameData.hiScoreToStore.addScore(GameData.SCORE_INCREMENT_PER_TICK);
                    spawnObstacle();
                }
            }

            public void onFinish() {
                // Increment speed and difficulty
                GameData.difficulty++;
                gameManager.incrementSpeed(2);
                GameData.hiScoreToStore.addScore(GameData.SCORE_INCREMENT_PER_DIFFICULTY);
                createBlockMovementCounter();
                lose();
            }
        }.start();
    }

    private void spawnObstacle(){
        Random random = new Random();
        boolean spawnTop = (random.nextInt(2) == 0);
        int stageHeightDifference = GameData.floorHeight - GameData.ceilingHeight;
        int objectHeight = stageHeightDifference / (2 + random.nextInt(10));
        int objectWidth = (int)GameData.BLOCK_SIZE;
        int spawnPositionY = (spawnTop) ? GameData.floorHeight - objectHeight/2 : GameData.ceilingHeight + objectHeight/2;

        BaseObstacle newObstacle = new BlockObstacle((int)screenSize.x, spawnPositionY, new Vector2(objectWidth, objectHeight));
        gameManager.obstacles.add(newObstacle);

        GameData.hiScoreToStore.addScore(GameData.SCORE_INCREMENT_PER_BLOCK);
    }

    private void drawFloor(Canvas canvas){
        paint.setColor(getResources().getColor(R.color.button_color_main));
        int offset = 5;
        canvas.drawLine(0, GameData.floorHeight + offset, screenSize.x, GameData.floorHeight + offset, paint);
        canvas.drawLine(0, GameData.floorHeight, screenSize.x, GameData.floorHeight, paint);
        canvas.drawLine(0, GameData.ceilingHeight, screenSize.x, GameData.ceilingHeight, paint);
        canvas.drawLine(0, GameData.ceilingHeight - offset, screenSize.x, GameData.ceilingHeight - offset, paint);
    }

    private void drawObstacles(Canvas canvas){
        for(BaseObstacle tile : gameManager.obstacles){
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.STROKE);

            tile.setCollisionRectangle();
            canvas.drawRect(tile.collisionRectangle, paint);
        }
        paint.setStyle(Paint.Style.FILL);
    }

    private void drawPlayer(Canvas canvas){
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);

        player.setCollisionRectangle();
        canvas.drawRect(player.collisionRectangle, paint);
        paint.setStyle(Paint.Style.FILL);
    }

    private void drawScore(Canvas canvas){
        paint.setColor(Color.WHITE);
        paint.setTextSize(96);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("" + GameData.hiScoreToStore.getScore(),
                    (int)screenSize.x/2,
                (int)screenSize.y/4,
                    paint
                );
    }

    public void lose(){
        if(!gameIsLost && !showLostWindow) {
            context.showLoseWindow();
            showLostWindow = true;
            gameIsLost = true;
            gameIsRunning = false;
        }
    }

    public void pause(boolean pause){
        gamePaused = pause;
    }

    public boolean gameIsPaused(){
        return gamePaused;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public boolean getGameIsLost(){
        return gameIsLost;
    }
}
