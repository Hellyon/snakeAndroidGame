package model.pickups;

/**
 * Strategy : Changes the given score of the pickup according to it's type
 *
 * @author Ilyace Benjelloun
 */
abstract class PickupStrategy {
    // Pickup Constants
    static final int APPLE = 1;
    static final int PEAR = 2;
    static final int CHERRY = 3;
    static final int BILL = 15;
    static final int HEN = -10;
    static final int RUBY = 0;

    /**
     * The strategy's method
     *
     * @return int : the pickup givenPoints
     */
    public abstract int getGivenPoints();
}
