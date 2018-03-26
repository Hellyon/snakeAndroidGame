package model.pickups;

/**
 * This is an Apple : Gives 1 points
 *
 * @author Ilyace Benjelloun
 */
public class Apple extends PickupStrategy {
    /**
     * Package-Private constructor
     */
    Apple() {
    }

    /**
     * @return givenPoints (1)
     */
    @Override
    public int getGivenPoints() {
        return APPLE;
    }
}
