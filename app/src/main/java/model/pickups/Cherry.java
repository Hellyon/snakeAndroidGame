package model.pickups;

/**
 * This is a Cherry : Gives 3 Points
 *
 * @author Ilyace Benjelloun
 */
public class Cherry extends PickupStrategy {
    /**
     * Package-Private constructor
     */
    Cherry() {
    }

    /**
     * @return givenPoints (3)
     */
    @Override
    public int getGivenPoints() {
        return CHERRY;
    }
}
