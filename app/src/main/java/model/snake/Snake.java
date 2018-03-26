package model.snake;

import android.graphics.Rect;

import java.util.ArrayList;

import tools.SharedConst;

/**
 * The Hun[G]ry snake !
 *
 * @author Ilyace Benjelloun
 */
public class Snake {
    /**
     * The snake length
     */
    private int length;
    /**
     * List of his bodies
     */
    private ArrayList<Body> body;
    /**
     * X Axe Position
     */
    private int xPosition;
    /**
     * Y Axe Position
     */
    private int yPosition;

    /**
     * Public Constructor
     */
    public Snake() {
        body = new ArrayList<>();
        length = SharedConst.BABY_SNAKE;
    }

    /**
     * length Getter
     *
     * @return int : length
     */
    public int getLength() {
        return length;
    }

    /**
     * Adds a body to the list of bodies
     *
     * @param body the newly added body
     */
    void addBody(Body body) {
        this.body.add(0, body);
        length++;
    }

    /**
     * Removes a body from the list of bodies (Unused)
     *
     * @param body the body to remove
     */
    public void removeBody(Body body) {
        this.body.remove(body);
        length--;
    }

    /**
     * body Getter
     *
     * @return List<Body> the list of bodies
     */
    public ArrayList<Body> getListBody() {
        return body;
    }

    /**
     * xPosition Getter
     *
     * @return int : xPosition
     */
    public int getXPosition() {
        return xPosition;
    }

    /**
     * xPosition Setter
     *
     * @param xPosition int : the current xPosition
     */
    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    /**
     * yPosition Getter
     *
     * @return int : xyPosition
     */
    public int getYPosition() {
        return yPosition;
    }

    /**
     * yPosition Setter
     *
     * @param yPosition int : the current yPosition
     */
    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    /**
     * create the Rect where the head part is displayed
     *
     * @param blockSize how much size is 1 block ?
     * @return Rect, the newly created Rect
     */
    public Rect createHeadRect(int blockSize) {
        return new Rect(getXPosition() * blockSize,
                getYPosition() * blockSize,
                (getXPosition() * blockSize) + blockSize,
                (getYPosition() * blockSize) + blockSize);
    }

}
