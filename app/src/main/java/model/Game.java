package model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

import model.pickups.Pickup;
import model.pickups.PickupType;
import model.snake.Body;
import model.snake.Snake;
import model.snake.SnakeManager;
import snake.snake.R;
import tools.SharedConst;

/**
 * Manage the game and the drawing
 *
 * @author Ilyace Benjelloun
 */
public class Game extends SurfaceView implements Runnable {
    // boolean Constants
    /**
     * Time to DRAW ?
     */
    private final static boolean DRAW = true;
    /**
     * NOT time to DRAW ?
     */
    private final static boolean NOT_DRAW = false;
    /**
     * Time to RESPAWN ?
     */
    private final static boolean RESPAWN = true;
    /**
     * NOT time to RESPAWN ?
     */
    private final static boolean NOT_RESPAWN = false;
    /**
     * RESPAWN time REPOP frames
     */
    private final static int REPOP = 80;

    /**
     * The size in segments of the playable area
     */
    public static final int NUM_BLOCKS_WIDE = 30;
    /**
     * There are 1000 milliseconds in a second
     */
    private final long MILLIS_PER_SECOND = 1000;
    // Load pickup graphics
    /**
     * Bitmap of the apple, will NEVER change - Drink apples like he said
     */
    private final Bitmap appleImage;
    /**
     * Bitmap of the pear, will NEVER change - Biotiful green
     */
    private final Bitmap pearImage;
    /**
     * Bitmap of the cherry, will NEVER change - I love cherry
     */
    private final Bitmap cherryImage;
    /**
     * Bitmap of the hen he, will NEVER change - He flies and... well unlucky
     */
    private final Bitmap henImage;
    /**
     * Bitmap of the bill, will NEVER change - Because everyone loves money
     */
    private final Bitmap billImage;
    /**
     * Bitmap of the ruby, will NEVER change - I'm not gonna eat that !
     */
    private final Bitmap rubyImage;
    /**
     * Bitmap of the apple, will NEVER change
     */
    private final Bitmap grass;

    //TODO : Increase difficulty according to the score/snake snakeLength
    /**
     * The thread for the main game Loop
     */
    private Thread thread = null;
    // TODO : sound effects
    /**
     * Where is the snake Heading  ?
     */
    private Heading heading;
    /**
     * Holds the screen size
     */
    private final int screenX;
    /**
     * The Screen Rect the redraw the background...
     */
    private final Rect screenRect;
    /**
     * Delicious snake pickup
     */
    private Pickup pickup;
    /**
     * The size of a segment (snake/pickup)
     */
    private final int blockSize;
    /**
     * Number of segments High
     */
    private final int numBlocksHigh;
    /**
     * Control the main Game update loop
     */
    private long nextFrameTime;
    /**
     * Control the pickup Respawn time Loop
     */
    private long nextPickupTime;
    /**
     * The Score of the player
     */
    private Score score;
    /**
     * Infernal mode activated !?
     */
    private final Difficulty difficulty;
    /**
     * The player Name, well you're not the only one
     */
    private final String playerName;

    // The hun[G]ry snake !
    private Snake snake;
    /**
     * Gives Super Powers !
     */
    private SnakeManager snakeManager;

    // Everything we need for drawing
    /**
     * Is the game currently playing?
     * volatile : isPlaying will always be up to date
     */
    private volatile boolean isPlaying;
    /**
     * Required to use canvas and draw
     */
    private final SurfaceHolder surfaceHolder;
    /**
     * Snake color... until real and no ugly sprites
     */
    private final Paint paint;
    /**
     * What did the player do ? I hear you trust me
     */
    private final OnGameEventListener listener;

    /**
     * Public Constructor
     *
     * @param context    Context : GameActivity
     * @param size       Point : Screen Display size
     * @param listener   onGameEventListener : GameEventListener
     * @param difficulty Difficulty : the player selected difficulty
     * @param playerName String : the name of the player
     */
    public Game(Context context, Point size, OnGameEventListener listener, Difficulty difficulty, String playerName) {
        super(context);
        this.listener = listener;

        //Initialize screen dimensions
        screenX = size.x;
        int screenY = size.y;

        grass = BitmapFactory.decodeResource(getResources(), R.drawable.grass);

        this.difficulty = difficulty;
        this.playerName = playerName;

        screenRect = new Rect(0, 0, size.x, size.y);
        // How many pixels each block is (dpi)
        blockSize = screenX / NUM_BLOCKS_WIDE;
        // Number of blocks for the height
        numBlocksHigh = screenY / blockSize;

        //Load Bitmaps for the Pickups
        appleImage = BitmapFactory.decodeResource(getResources(), R.drawable.apple);
        pearImage = BitmapFactory.decodeResource(getResources(), R.drawable.pear);
        cherryImage = BitmapFactory.decodeResource(getResources(), R.drawable.cherry);
        billImage = BitmapFactory.decodeResource(getResources(), R.drawable.bill);
        henImage = BitmapFactory.decodeResource(getResources(), R.drawable.hen);
        rubyImage = BitmapFactory.decodeResource(getResources(), R.drawable.ruby);

        // Initialize the drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();
        // Start the game by Heading to the Right
        heading = Heading.RIGHT;
    }

    /**
     * Updates the view FPS times in a second
     */
    @Override
    public void run() {
        // Doing some sports mate ?
        while (isPlaying) {
            // The food is gone
            if (updatePickupRequired()) {
                createPickup();
            }
            // Next frame ?
            if (updateRequired()) {
                update();
                draw();
            }
        }
    }

    /**
     * Pauses the game
     */
    public void pause() {
        // Not here ? Don't worry got that for you
        if (isPlaying) {
            isPlaying = false;
            try {
                thread.join();
            } catch (InterruptedException e) {
                // Error
            }
        }
    }

    /**
     * Resumes the game
     */
    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Starts a new game, initializes the snake and the score for the current session
     */
    public void newGame() {
        // Start with a single snake segment (only the head)
        snake = new Snake();
        snakeManager = new SnakeManager(snake);

        snake.setXPosition(NUM_BLOCKS_WIDE / 2);
        snake.setYPosition(numBlocksHigh / 2);

        // Places the delicious snake pickup to a random location
        createPickup();

        // Reset the score
        score = new Score(playerName, difficulty.getDifficulty());
        listener.onNewGame();
        // Setup nextFrameTime so an update is triggered
        nextFrameTime = System.currentTimeMillis();
    }

    /**
     * Spawns the pickup to a random location
     */
    private void createPickup() {
        // Create a pickup with a random yPosition and yPosition
        Random random = new Random();
        int pickupX = random.nextInt(NUM_BLOCKS_WIDE - 1) + 1;
        int pickupY = random.nextInt(numBlocksHigh - 1) + 1;
        // Random Type
        int rtype = random.nextInt(30);
        // Create a new pickup
        PickupType type = getRandomType(rtype);
        switch (type) {
            case APPLE:
                pickup = new Pickup(pickupX, pickupY, appleImage, type);
                break;
            case HEN:
                pickup = new Pickup(pickupX, pickupY, henImage, type);
                break;
            case BILL:
                pickup = new Pickup(pickupX, pickupY, billImage, type);
                break;
            case PEAR:
                pickup = new Pickup(pickupX, pickupY, pearImage, type);
                break;
            case RUBY:
                pickup = new Pickup(pickupX, pickupY, rubyImage, type);
                break;
            case CHERRY:
                pickup = new Pickup(pickupX, pickupY, cherryImage, type);
                break;
        }
        // Set up the next time the pickup has to be respawned if not eaten within REPOP frames
        nextPickupTime = System.currentTimeMillis() + (MILLIS_PER_SECOND / difficulty.getFramePerSec()) * REPOP;
    }

    /**
     * Returns the the pickup type according to the randomType given
     *
     * @param randomType int
     * @return PickupType
     */
    private PickupType getRandomType(int randomType) {
        // Quick Maths
        if (randomType % 2 == 0) return PickupType.APPLE;
        if (randomType % 3 == 0) return PickupType.PEAR;
        if (randomType % 5 == 0) return PickupType.CHERRY;
        if (randomType % 7 == 0) return PickupType.BILL;
        if (randomType % 11 == 0) return PickupType.HEN;
        if (randomType % 13 == 0) return PickupType.RUBY;
        return PickupType.PEAR;
    }

    /**
     * Has the snake died ?
     *
     * @return true if DEAD, false if NOT_DEAD
     */
    private boolean detectDeath() {
        // Has the snake died ?
        if (snakeManager.isOutOfEdge(numBlocksHigh) || snakeManager.hasEatenHimself())
            return SharedConst.DEAD;
        // He is alive !
        return SharedConst.NOT_DEAD;
    }

    /**
     * Updates the game : spawns another pickup if the snake ate 1 and adds a point to the score
     * Moves the snake to the selected location
     * Detects if the snake has died, in this case, starts a new game
     */
    private void update() {
        // Time to eat !
        if (snake.getXPosition() == pickup.getXPosition() && snake.getYPosition() == pickup.getYPosition()) {
            snakeManager.eatFood();
            //add to the score
            score.addScore(pickup.getGivenPoints());
            //replace pickup
            createPickup();
            this.listener.onSnakeAte(score.getScore());
        }
        // Doing some sports mate ?
        snakeManager.moveSnake(heading);
        // Has the snake died ?
        if (detectDeath()) {
            //start again
            score.setSnakeLength(snake.getLength());
            this.listener.onSnakeDied(score);
            newGame();
        }
    }

    /**
     * Draws the game : 1st the background, 2nd the pickups, 3rd the snake
     */
    private void draw() {
        // Get a lock on the canvas
        if (surfaceHolder.getSurface().isValid()) {
            Canvas canvas = surfaceHolder.lockCanvas();

            // Fill the screen with biotifull green grass
            canvas.drawBitmap(grass, null, screenRect, null);

            // Set the color of the snake to white
            paint.setColor(Color.argb(255, 255, 255, 255));

            // Draw the pickups
            if (pickup != null) {
                canvas.drawBitmap(pickup.getPickupImage(), null, pickup.createPickupRect(blockSize), paint);
            }
            // Draw the snake block by block starting with the head
            if (snake != null) {
                canvas.drawRect(snake.createHeadRect(blockSize), paint);
                for (Body body : snake.getListBody()) {
                    canvas.drawRect(body.createBodyRect(blockSize), paint);
                }
            }
            // Unlock the canvas and reveal the graphics for this frame
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    /**
     * Tests if the game has to be updated (8/10/13 times a second depending on difficulty)
     *
     * @return true if Time to Draw, return false if Not Time to Draw
     */
    private boolean updateRequired() {
        if (nextFrameTime <= System.currentTimeMillis()) {
            // 1 Frame has passed, time to update !

            // Setup when the next update will be triggered
            nextFrameTime = System.currentTimeMillis() + MILLIS_PER_SECOND / difficulty.getFramePerSec();

            // Time to draw ! :)
            return DRAW;
        }
        // Not time to draw :(
        return NOT_DRAW;
    }

    /**
     * Tests if the pickups has to be replaced (if not eaten within 80 game updates)
     *
     * @return true if Time to Respawn, return false if Not Time to Respawn
     */
    private boolean updatePickupRequired() {
        if (nextPickupTime <= System.currentTimeMillis()) {
            // 1 Frame has passed, time to update !

            // Setup when the next update will be triggered
            nextPickupTime = System.currentTimeMillis() + (MILLIS_PER_SECOND / difficulty.getFramePerSec()) * REPOP;

            // Time to draw ! :)
            return RESPAWN;
        }
        // Not time to draw :(
        return NOT_RESPAWN;
    }

    /**
     * Changes the heading of the snake depending on where the player tapped on the screen
     *
     * @param motionEvent MotionEvent
     * @return true
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        if (!isPlaying && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            listener.onUserResume(score.getScore());
            return true;
        }
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            // Triggers when you tap the screen (not release)
            case MotionEvent.ACTION_DOWN:
                // Where did you tap the screen TOP or BOTTOM ?
                if (motionEvent.getX() >= screenX / 2) {
                    switch (heading) {
                        // The snake was going UP
                        case UP:
                            heading = Heading.RIGHT;
                            break;
                        // The snake was going RIGHT
                        case RIGHT:
                            heading = Heading.DOWN;
                            break;
                        // The snake was going DOWN
                        case DOWN:
                            heading = Heading.LEFT;
                            break;
                        // The snake was going LEFT
                        case LEFT:
                            heading = Heading.UP;
                            break;
                    }
                } else {
                    switch (heading) {
                        // The snake was going UP
                        case UP:
                            heading = Heading.LEFT;
                            break;
                        // The snake was going LEFT
                        case LEFT:
                            heading = Heading.DOWN;
                            break;
                        // The snake was going DOWN
                        case DOWN:
                            heading = Heading.RIGHT;
                            break;
                        // The snake was going RIGHT
                        case RIGHT:
                            heading = Heading.UP;
                            break;
                    }
                }
        }
        return true;
    }
}