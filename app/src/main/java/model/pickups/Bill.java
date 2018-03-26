package model.pickups;

/**
 * This is a Bill : Gives 15 Points
 *
 * @author Ilyace Benjelloun
 */
public class Bill extends PickupStrategy {
    /**
     * Package-Private constructor
     */
    Bill() {
    }

    /**
     * @return givenPoints (15)
     */
    @Override
    public int getGivenPoints() {
        return BILL;
    }
}
