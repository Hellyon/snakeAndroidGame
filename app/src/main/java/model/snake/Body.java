package model.snake;

import android.graphics.Rect;

/**
 * The snake is composed of many bodies
 *
 * @author Ilyace Benjelloun
 */
public class Body {
    /**
     * X Axe position
     */
    private int xPosition;
    /**
     * Y Axe Position
     */
    private int yPosition;
    /**
     * Previous body part of the body part(to make the snake move)
     */
    private final Body lastBody;

    /**
     * Package-private Constructor
     *
     * @param lastBody the previous body part of the body part
     */
    Body(Body lastBody) {
        this.lastBody = lastBody;
    }

    /**
     * Package-private Constructor in case this is the 1st body part of the snake
     */
    Body() {
        this(null);
    }

    /**
     * lastBody Getter
     *
     * @return Body : the last bodyPart
     */
    Body getLastBody() {
        return lastBody;
    }

    /**
     * xPosition Getter
     *
     * @return int : xPosition
     */
    int getXPosition() {
        return xPosition;
    }

    /**
     * xPosition Setter
     *
     * @param xPosition int : the current xPosition
     */
    void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    /**
     * yPosition Getter
     *
     * @return int : yPosition
     */
    int getYPosition() {
        return yPosition;
    }

    /**
     * yPosition Setter
     *
     * @param yPosition int : the current yPosition
     */
    void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    /**
     * create the Rect where the body part is displayed
     *
     * @param blockSize how much size is 1 block ?
     * @return Rect, the newly created Rect
     */
    public Rect createBodyRect(int blockSize) {
        return new Rect(getXPosition() * blockSize,
                getYPosition() * blockSize,
                getXPosition() * blockSize + blockSize,
                getYPosition() * blockSize + blockSize);
    }
}
