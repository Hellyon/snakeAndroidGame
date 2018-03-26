package model.snake;

import model.Game;
import model.Heading;
import tools.SharedConst;

/**
 * The snake super powers, it can move and eat !
 *
 * @author Ilyace Benjelloun
 */
public class SnakeManager {
    /**
     * Minimum length for he snake eating himself (so Hun[G]ry !)
     */
    private final static int MIN_EAT_HIMSELF_LENGTH = 3;
    /**
     * First body in the list = 0
     */
    private final static int FIRST_BODY = 0;
    /**
     * Going Up
     */
    private final static int UP = -1;
    /**
     * Going Left
     */
    private final static int LEFT = -1;
    /**
     * Going Down
     */
    private final static int DOWN = 1;
    /**
     * Going Right
     */
    private final static int RIGHT = 1;
    /**
     * The Hun[G]ry snake !
     */
    private final Snake snake;

    /**
     * Public Constructor
     *
     * @param snake Snake the current snake
     */
    public SnakeManager(Snake snake) {
        this.snake = snake;
    }

    /**
     * Moves the snake on the board
     *
     * @param heading Heading : Where is the snake heading to ?
     */
    public void moveSnake(Heading heading) {
        // Move the body
        for (Body body : snake.getListBody()) {
            // Start at the back and move the body to the body part or the head in front of it
            // Is it the FIRST_BODY part ?
            if (body.getLastBody() != null) {
                body.setXPosition(body.getLastBody().getXPosition());
                body.setYPosition((body.getLastBody().getYPosition()));
            }
            // if Not
            else {
                body.setXPosition(snake.getXPosition());
                body.setYPosition(snake.getYPosition());
            }
        }

        // Move the head of the Hun[G]ry snake in the appropriate heading
        switch (heading) {
            case UP:
                snake.setYPosition(snake.getYPosition() + UP);
                break;

            case RIGHT:
                snake.setXPosition(snake.getXPosition() + RIGHT);
                break;

            case DOWN:
                snake.setYPosition(snake.getYPosition() + DOWN);
                break;

            case LEFT:
                snake.setXPosition(snake.getXPosition() + LEFT);
                break;
        }
    }

    /**
     * Is the snake Out of edge of the board?
     *
     * @param numBlocksHigh how much High is 1 block ?
     * @return boolean : true if DEAD, false if NOT_DEAD
     */
    public boolean isOutOfEdge(int numBlocksHigh) {
        // Has the snake died ?
        if (snake.getXPosition() == -1) return SharedConst.DEAD;
        if (snake.getXPosition() >= Game.NUM_BLOCKS_WIDE) return SharedConst.DEAD;
        if (snake.getYPosition() == -1) return SharedConst.DEAD;
        if (snake.getYPosition() == numBlocksHigh) return SharedConst.DEAD;
        // He is alive !
        return SharedConst.NOT_DEAD;
    }

    /**
     * Has the Hun[G]ry snake eaten some food ?
     */
    public void eatFood() {
        // He ate something ! He is getting longer now
        if (snake.getLength() > SharedConst.BABY_SNAKE) {
            snake.addBody(new Body(snake.getListBody().get(FIRST_BODY)));
        }
        // Doing some sports mate ?
        else {
            snake.addBody(new Body());
        }
    }

    /**
     * Has the Hun[G]ry snake eaten himself ? Too much famished I guess
     *
     * @return boolean : true if DEAD, false if NOT_DEAD
     */
    public boolean hasEatenHimself() {
        for (Body body : snake.getListBody()) {
            if ((snake.getLength() > MIN_EAT_HIMSELF_LENGTH) && (snake.getXPosition() == body.getXPosition()) && (snake.getYPosition() == body.getYPosition())) {
                return SharedConst.DEAD;
            }
        }
        // Not that famished
        return SharedConst.NOT_DEAD;
    }
}
