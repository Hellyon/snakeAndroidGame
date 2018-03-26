package model;

/**
 * Game Events Listeners
 *
 * @author Ilyace Benjelloun
 */
public interface OnGameEventListener {
    void onSnakeAte(int score);

    void onSnakeDied(Score score);

    void onUserResume(int score);

    void onNewGame();
}