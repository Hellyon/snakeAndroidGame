package model.pickups;

/**
 * This is a Hen : Gives -10 Points
 *
 * @author Ilyace Benjelloun
 */
public class Hen extends PickupStrategy {
    /**
     * Package-Private constructor
     */
    Hen() {
    }

    /**
     * @return givenPoints (-10)
     */
    @Override
    public int getGivenPoints() {
        return HEN;
    }
}
