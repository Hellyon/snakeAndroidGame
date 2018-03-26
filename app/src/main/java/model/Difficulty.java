package model;

/**
 * Changes the speed of the snake according to the selected difficulty
 *
 * @author Ilyace Benjelloun
 */
public class Difficulty {
    /**
     * Number of frames per second, how many times the game refreshes a second
     */
    private final int framePerSec;
    /**
     * Selected difficulty
     */
    private final int difficulty;

    /**
     * Public constructor
     *
     * @param difficulty int
     */
    public Difficulty(int difficulty) {
        this.framePerSec = difficulty;
        this.difficulty = difficulty;
    }

    /**
     * difficulty getter
     *
     * @return int
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * framePerSec getter
     *
     * @return int
     */
    int getFramePerSec() {
        return framePerSec;
    }
}
