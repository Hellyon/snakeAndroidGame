package views;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.FrameLayout;
import android.widget.TextView;

import model.Difficulty;
import model.Game;
import model.OnGameEventListener;
import model.Score;
import snake.snake.R;
import tools.MusicSingleton;
import tools.SharedConst;
import tools.UtilitiesActivity;


/**
 * GameActivity, where the game takes place
 *
 * @author Ilyace Benjelloun
 */
public class GameActivity extends AppCompatActivity implements OnGameEventListener {
    /**
     * Holds a reference to the Game engine
     */
    private Game game;
    /**
     * Holds a reference to the scoreView
     */
    private TextView scoreView;
    /**
     * Holds a reference to the FireBaseFragment
     */
    private FireBaseFragment fireBaseFragment;

    /**
     * Initialize the GameActivity
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Restart the music according to user choice
        MusicSingleton.getInstance(getApplicationContext()).start();

        // Gets everything to create the difficulty
        Intent intent = getIntent();
        final int difficulty = intent.getIntExtra(getString(R.string.difficulty), SharedConst.MEDIUM);
        final String playerName = intent.getStringExtra(getString(R.string.player_name));

        // Hides status bar
        tools.UtilitiesActivity.hideStatusBar(this);
        // Locks the screen Orientation tu Current or Landscape depending on device
        tools.UtilitiesActivity.lockScreenOrientation(this);

        loadFireBaseFragment();
        //Dimensions de l'écran
        Display display = getWindowManager().getDefaultDisplay();
        // Initialize the result into a Point object
        Point size = new Point();
        display.getSize(size);

        // Make game the view of the Activity
        setContentView(R.layout.game_activity);

        // Initialize the Game engine
        game = new Game(this, size, this, new Difficulty(difficulty), playerName);

        // Initialize the game Layout and the scoreView
        game.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        FrameLayout frameLayoutRoot = findViewById(R.id.frameLayoutRoot);
        frameLayoutRoot.addView(game, 0);
        scoreView = findViewById(R.id.scoreView);

        //Launch the game
        game.newGame();
        game.resume();
    }

    /**
     * Stop the thread in Game and the music
     * Updates the scoreView onPause
     */
    @Override
    protected void onPause() {
        super.onPause();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scoreView.setText(getString(R.string.pause));
            }
        });
        MusicSingleton.getInstance(getApplicationContext()).pause();
        game.pause();
    }

    /**
     * Hides the status bar onStart
     */
    @Override
    protected void onStart() {
        super.onStart();
        // Hides status bar
        tools.UtilitiesActivity.hideStatusBar(this);
    }

    /**
     * Update the scoreView onSnakeAte
     *
     * @param score the score to display
     */
    @Override
    public void onSnakeAte(final int score) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scoreView.setText(getString(R.string.score, score));
            }
        });
    }

    /**
     * Writes the new score on the fireBase database onSnakeDied (feelsBadMan)
     *
     * @param score the score to write
     */
    @Override
    public void onSnakeDied(Score score) {
        fireBaseFragment.getDatabaseHandler().writeNewScore(score);
    }

    /**
     * Update the scoreView onNewGame
     */
    @Override
    public void onNewGame() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scoreView.setText(getString(R.string.score, 0));
            }
        });
    }

    /**
     * Resumes the thread in game and updates the scoreView onUserResume
     *
     * @param score the score to display
     */
    @Override
    public void onUserResume(final int score) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scoreView.setText(getString(R.string.score, score));
            }
        });
        game.resume();
    }

    /**
     * Reloads the FireBaseFragment onCreate
     */
    private void loadFireBaseFragment() {
        fireBaseFragment = new FireBaseFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.score_fragment, fireBaseFragment).addToBackStack(null).commit();
    }

    /**
     * Resumes the music onRestart
     */
    @Override
    public void onRestart() {
        super.onRestart();
        MusicSingleton.getInstance(getApplicationContext()).start();
        UtilitiesActivity.hideStatusBar(this);
    }

    /**
     * Because NO ONE wants to press return twice to go back to main screen
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
