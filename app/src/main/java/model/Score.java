package model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

import tools.SharedConst;

/**
 * Track the score of the player
 *
 * @author Ilyace Benjelloun
 */
@IgnoreExtraProperties
public class Score {
    /**
     * Some magic for the LeaderBoards
     */
    private static final int REVERSE_DATABASE = -1;
    /**
     * The playerName
     */
    private String player;
    /**
     * The number of points
     */
    private int score;
    /**
     * The selected Difficulty
     */
    private int difficulty;
    /**
     * The length of the snake at the end of the game
     */
    private int snakeLength;

    /**
     * Public Empty Constructor for Firebase
     */
    public Score() {
    }

    /**
     * Public Constructor
     *
     * @param player     String : the name of the player
     * @param difficulty int : the Selected difficulty (only for LeaderBoards)
     */
    public Score(String player, int difficulty) {
        score = SharedConst.NEW_SCORE;
        this.player = player;
        this.difficulty = difficulty;
    }

    /**
     * difficulty Getter
     *
     * @return int : difficulty
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * score getter
     *
     * @return int : score
     */
    public int getScore() {
        return score;
    }

    /**
     * Reverse the score for the LeaderBoards (Magics)
     */
    public void reverseScore() {
        score *= REVERSE_DATABASE;
    }

    /**
     * Add to the score
     *
     * @param points int :number of points scored
     */
    void addScore(int points) {
        score += points;
    }

    /**
     * player Getter
     *
     * @return player String : the name of the player
     */
    public String getPlayer() {
        return player;
    }

    /**
     * snakeLength Setter used when the snake dies
     *
     * @param snakeLength int : snakeLength
     */
    void setSnakeLength(int snakeLength) {
        this.snakeLength = snakeLength;
    }

    /**
     * snakeLength Getter
     *
     * @return snakeLength
     */
    public int getSnakeLength() {
        return snakeLength;
    }

    /**
     * Maps the scores => for Firebase
     *
     * @return result Map<String,Score> the newly written score
     */
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("score", score * REVERSE_DATABASE);
        result.put("snakeLength", snakeLength);
        result.put("difficulty", difficulty);
        result.put("player", player);

        return result;
    }
}
