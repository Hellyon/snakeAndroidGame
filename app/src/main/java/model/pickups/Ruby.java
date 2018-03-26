package model.pickups;

/**
 * This is a Ruby : Gives 0 points
 *
 * @author Ilyace Benjelloun
 */
public class Ruby extends PickupStrategy {
    /**
     * Package-Private constructor
     */
    Ruby() {
    }

    /**
     * @return givenPoints (0)
     */
    @Override
    public int getGivenPoints() {
        return RUBY;
    }
}
