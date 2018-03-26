package views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import snake.snake.R;
import tools.MusicSingleton;
import tools.UtilitiesActivity;

/**
 * TutorialActivity
 *
 * @author Alex Cardoso
 * @author Ilyace Benjelloun
 */
public class TutorialActivity extends AppCompatActivity {
    /**
     * Initialize the TutorialActivity
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_activity);

        // Restart the music according to user choice
        MusicSingleton.getInstance(getApplicationContext()).start();

        // Hides status bar
        tools.UtilitiesActivity.hideStatusBar(this);
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

}