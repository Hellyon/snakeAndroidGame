package views;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import model.Score;
import snake.snake.R;
import tools.MusicSingleton;
import tools.UtilitiesActivity;


/**
 * ScoresActivity
 *
 * @author Ilyace Benjelloun
 */
public class ScoresActivity extends AppCompatActivity {
    /**
     * Hold a reference to the ListView to update it
     */
    private ListView scoresView;
    /**
     * Hold a reference to the FireBaseFragment to get the lists of scores
     */
    private FireBaseFragment fireBaseFragment;
    /**
     * Hold a reference to the name of the player
     */
    private String player;
    /**
     * Hold a reference to the TextView to update the text displayed
     */
    private TextView pressView;

    /**
     * Initialize the Scores Activity
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_activity);

        scoresView = findViewById(R.id.scoreList);
        initializeFireBaseFragment();
        // Lock the Screen Rotation
        UtilitiesActivity.lockScreenOrientation(this);
        // Hides status bar
        tools.UtilitiesActivity.hideStatusBar(this);
        // Restart the music according to user choice
        MusicSingleton.getInstance(getApplicationContext()).start();


        loadFireBaseFragment();

        // Preselect which list to display
        String playerName = getIntent().getStringExtra(getString(R.string.player_name));
        if (playerName == null) {
            player = null;
        } else {
            player = playerName;
        }

        // Display the message that prompt the user to tap the screen (once... or twice)
        pressView = findViewById(R.id.press_to_update);
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
     * Used to link the scoresList to the ListView depending on which LeaderBoard the user want to see
     *
     * @param scores List<Score> the list of scores to display
     */
    private void linkScoresToListView(List<Score> scores) {
        final ScoreAdapter adapter = new ScoreAdapter(this, scores);
        scoresView.setAdapter(adapter);
    }

    /**
     * Stop the music onPause
     */
    @Override
    public void onPause() {
        super.onPause();
        MusicSingleton.getInstance(getApplicationContext()).pause();
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
     * Initialize FireBaseFragment on create
     */
    private void initializeFireBaseFragment() {
        // Load Fragment Manager
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Initialize Transaction
        FragmentTransaction fragmentTransactionFireBase = fragmentManager.beginTransaction();
        // Adds FireBaseFragment
        FireBaseFragment fireBaseFragment = new FireBaseFragment();
        fragmentTransactionFireBase.add(R.id.score_fragment, fireBaseFragment);
        // Commit first transaction
        fragmentTransactionFireBase.commit();
    }

    /**
     * Because NO ONE wants to press return twice to go back to main screen
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * Updates the scores list when the user touches the screen
     *
     * @param motionEvent motion event
     * @return true
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        List<Score> scores;
        if (player == null) {
            scores = fireBaseFragment.getDatabaseHandler().getScores();
        } else {
            scores = fireBaseFragment.getDatabaseHandler().getPlayerScores(player);
        }
        pressView.setText(R.string.empty);
        linkScoresToListView(scores);
        return true;
    }
}
