package com.game.twinghosts.elementalclimber.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import com.game.twinghosts.elementalclimber.Camera.Camera;
import com.game.twinghosts.elementalclimber.Data.GameData;
import com.game.twinghosts.elementalclimber.Data.GameManager;
import com.game.twinghosts.elementalclimber.GameObjects.Player;
import com.game.twinghosts.elementalclimber.GameObjects.Tiles.BasicTile;
import com.game.twinghosts.elementalclimber.GameObjects.Tiles.TileSingle;
import com.game.twinghosts.elementalclimber.R;
import com.game.twinghosts.elementalclimber.Utility.Vector2;

import java.util.Random;

class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private Player player;
    private Camera camera;
    private Paint paint = new Paint();

    private BasicTile[][] floorTiles = new BasicTile[GameData.STAGE_WIDTH][10];
    private GameManager gameManager;
    private CountDownTimer countDownTimer;
    private Vector2 screenSize;
    private boolean gameIsRunning = true;

    private int difficultyTickTime = 250;
    private int difficultyTimeReduction = 25;

    private boolean gamePaused = false;

    public GameView(Context context){
        super(context);

        getHolder().addCallback(this);

        // Store the screen width and height
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenSize = new Vector2(size.x, size.y);

        gameManager = new GameManager(context);

        // Define the block size based on the resolution
        GameData.setBlockSizeBasedOnResolution(screenSize.x);

        // Create a new camera which will act as the point where the world gets translated from
        camera = new Camera(new Vector2(0 + GameData.BLOCK_SIZE/2f, -screenSize.y + GameData.BLOCK_SIZE * 1.5f));

        // Create the player
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.block_white), screenSize);
        player.position = new Vector2(200, 200);

        // Run the game on a custom mainThread
        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        createStage(context);
        camera.setTarget(player);
    }

    /**
     * Updates the game before the draw function happens so that all objects are in the right place
     * before drawing them.
     */
    public void update() {
        if(gameIsRunning) {
            if(!gamePaused) {
                player.update(gameManager);
                camera.chaseTarget();
            }
        } else {
            if(player != null) {
                countDownTimer.cancel();
                player = null;
                lose();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(canvas == null) return;
        canvas.drawColor(Color.BLACK);

        // Draw the floor tiles
        drawFloorTiles(canvas);

        // Draw all of the tiles
        drawBlocks(canvas);

        player.draw(canvas);

        paint.setColor(Color.BLACK);
    }

    /**
     * Creates the stage array used for instantiating the GameObjects based on the level index
     */
    private void createStage(Context context){
        createBlockMovementCounter();
        createFloor(context);
    }

    /**
     * Creates the the floor tiles below the player and stores them seperately so that they don't
     * get used for collision detection. (A single height check is used to determine their top)
     * @param context the view's main context to get resources
     */
    private void createFloor(Context context){
        for(int i = 0; i < GameData.STAGE_WIDTH; i++){
            floorTiles[i][0] = new TileSingle(BitmapFactory.decodeResource(context.getResources(), R.drawable.block_white));
        }
    }

    private void createNextTile(int x, int y){
        BasicTile tile = gameManager.getRandomTile();
        tile.position = new Vector2(x, y);
        gameManager.setCurrentTile(tile);
        gameManager.tiles.add(tile);
    }

    private void createBlockMovementCounter(){
        int veryLargeInt = 100000;
        countDownTimer = new CountDownTimer(veryLargeInt * 1000, (long)GameData.clamp(difficultyTickTime - difficultyTimeReduction * gameManager.getDifficulty(), 125, 1000)) {
            public void onTick(long millisUntilFinished) {
                // Score increment per tick
                if(!gamePaused) {
                    gameManager.incrementScore(GameManager.SCORE_INCREMENT_PER_BLOCK);

                    // When there is a current block, drop it down
                    if (gameManager.getCurrentTile() != null) {
                        gameManager.getCurrentTile().position.y += GameData.BLOCK_SIZE;

                        // Once the block has landed, set the current on to null to start creating a new one the next tick
                        if (gameManager.getCurrentTile().position.y >= screenSize.y - GameData.BLOCK_SIZE * 2f) {
                            gameManager.setCurrentTile(null);
                        } else {
                            // Loop through all of the tiles to check for collision
                            for (BasicTile tile : gameManager.tiles) {
                                if (tile != gameManager.getCurrentTile() && gameManager.getCurrentTile().nextCollisionRect().intersect(
                                        new Rect(tile.collisionRectangle.left, tile.collisionRectangle.top - (int) GameData.BLOCK_SIZE, tile.collisionRectangle.right, tile.collisionRectangle.bottom - (int) GameData.BLOCK_SIZE))) {
                                    gameManager.setCurrentTile(null);
                                    return;
                                }
                            }
                        }

                        // When there is no current block, create a new one in a random lane
                    } else {
                        int index = new Random().nextInt(GameData.STAGE_WIDTH);
                        createNextTile((int) (index * GameData.BLOCK_SIZE), (int) (-screenSize.y / 2f - GameData.BLOCK_SIZE * 2.33333f));
                        gameManager.getCurrentTile().resizeImage(new Vector2(GameData.BLOCK_SIZE, GameData.BLOCK_SIZE));

                        // Add score per block dropped
                        gameManager.incrementScore(GameManager.SCORE_INCREMENT_PER_BLOCK);

                        // Increase difficulty when 10 blocks have been placed
                        if (gameManager.tiles.size() % GameData.DIFFICULTY_INCREASE_BLOCKS == 0) {
                            gameManager.incrementDifficulty(1);
                        }
                    }
                }
            }

            public void onFinish() {
                // Keep the timer going forever
                createBlockMovementCounter();
            }
        }.start();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setMovementButtons(FloatingActionButton leftButton, FloatingActionButton rightButton, FloatingActionButton jumpButton){
        leftButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(gameIsRunning && !gamePaused) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        player.moveLeft = true;
                    else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                        player.moveLeft = false;
                    return false;
                }
                return false;
            }

        });

        rightButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(gameIsRunning && !gamePaused) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        player.moveRight = true;
                    else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                        player.moveRight = false;
                    return false;
                }
                return false;
            }
        });

        jumpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gameIsRunning && gamePaused)
                    player.jump();
            }
        });
    }

    private void drawFloorTiles(Canvas canvas){
        for(int i = 0; i < floorTiles[0].length; i++){
            for(int j = 0; j < floorTiles[1].length; j++){
                float xCenterPos = i * GameData.BLOCK_SIZE + GameData.BLOCK_SIZE/2 - (int) camera.position.x;
                float yCenterPos = j * GameData.BLOCK_SIZE + GameData.BLOCK_SIZE/2 - (int) camera.position.y;
                if(floorTiles[i][j] != null){
                    floorTiles[i][j].position = new Vector2(xCenterPos, yCenterPos);
                    if(floorTiles[i][j].getSize().x != (int)GameData.BLOCK_SIZE)
                        floorTiles[i][j].resizeImage(new Vector2(GameData.BLOCK_SIZE, GameData.BLOCK_SIZE));
                    floorTiles[i][j].draw(canvas);
                }
            }
        }
    }

    private void drawBlocks(Canvas canvas){
        for(BasicTile tile : gameManager.tiles){
            tile.draw(canvas);
        }
    }

    private void lose(){
        // TODO show menu - RESTART, SUBMIT or QUIT
        toHiScoreScreen();
    }

    private void toHiScoreScreen(){
        Intent hiScoreIntent = new Intent(getContext(), PostGameActivity.class);
        hiScoreIntent.putExtra("score", gameManager.getScore());
        getContext().startActivity(hiScoreIntent);
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
}
