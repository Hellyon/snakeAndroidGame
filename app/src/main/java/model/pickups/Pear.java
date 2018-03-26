package model.pickups;

/**
 * This is a Pear : Gives 2 Points
 *
 * @author Ilyace Benjelloun
 */
public class Pear extends PickupStrategy {
    /**
     * Package-Private constructor
     */
    Pear() {
    }

    /**
     * @return givenPoints (2)
     */
    @Override
    public int getGivenPoints() {
        return PEAR;
    }
}
