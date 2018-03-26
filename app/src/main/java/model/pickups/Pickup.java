package model.pickups;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Pickups the player has to get
 *
 * @author Ilyace Benjelloun
 */
public class Pickup {
    /**
     * X Axe position
     */
    private final int xPosition;
    /**
     * Y Axe position
     */
    private final int yPosition;
    /**
     * Displayed Bitmap of the pickup
     */
    private final Bitmap pickupImage;
    /**
     * Strategy : return givenPoints
     */
    private PickupStrategy givenPoints;

    /**
     * Public Constructor
     *
     * @param xPosition int : xPosition
     * @param yPosition int : yPosition
     * @param image     Bitmap : Displayed bitmap of the pickup
     * @param type      PickupType
     */
    public Pickup(int xPosition, int yPosition, Bitmap image, PickupType type) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        pickupImage = image;
        // Depending on type, create the strategy
        switch (type) {
            case APPLE:
                givenPoints = new Apple();
                break;
            case HEN:
                givenPoints = new Hen();
                break;
            case BILL:
                givenPoints = new Bill();
                break;
            case PEAR:
                givenPoints = new Pear();
                break;
            case RUBY:
                givenPoints = new Ruby();
                break;
            case CHERRY:
                givenPoints = new Cherry();
                break;
        }
    }

    /**
     * yPosition getter
     *
     * @return int : yPosition
     */
    public int getYPosition() {
        return yPosition;
    }

    /**
     * yPosition getter
     *
     * @return int : yPosition
     */
    public int getXPosition() {
        return xPosition;
    }

    /**
     * create the Rect where the pickup is displayed
     *
     * @param blockSize how much size is 1 block ?
     * @return Rect, the newly created Rect
     */
    public Rect createPickupRect(int blockSize) {
        return new Rect(getXPosition() * blockSize,
                getYPosition() * blockSize,
                (getXPosition() * blockSize) + blockSize,
                (getYPosition() * blockSize) + blockSize);
    }

    /**
     * pickupImage getter
     *
     * @return Bitmap, the foodImage
     */
    public Bitmap getPickupImage() {
        return pickupImage;
    }

    /**
     * givenPoints Getter
     *
     * @return int : givenPoints
     */
    public int getGivenPoints() {
        return givenPoints.getGivenPoints();
    }

}
