package views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import snake.snake.R;
import tools.MusicSingleton;
import tools.SharedConst;
import tools.UtilitiesActivity;

/**
 * MainActivity
 *
 * @author Ilyace Benjelloun
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Hold a reference to the player name to send it to the GameActivity or the ScoresActivity
     */
    private String playerName;
    /**
     * Hold a reference to the EditText
     */
    private EditText nickNameEdit;

    /**
     * Initialize the MainActivity
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting Preferences
        final boolean settings = getSharedPreferences(getString(R.string.musicSettings), MODE_PRIVATE).getBoolean(getString(R.string.musicSettings), true);
        final String presetNick = getPreferences(MODE_PRIVATE).getString(getString(R.string.player_name), "Anonymous");

        // Initialize Music
        MusicSingleton.getInstance(getApplicationContext()).changeMusicState(settings);
        // Hides status bar
        tools.UtilitiesActivity.hideStatusBar(this);

        // Initialize Buttons
        final Button buttonPlay = findViewById(R.id.button_play);
        final Button buttonSettings = findViewById(R.id.button_settings);
        final Button buttonTutorial = findViewById(R.id.button_tutorial);
        final Button buttonBestScores = findViewById(R.id.button_scores);
        final Button buttonYourScores = findViewById(R.id.button_yourScores);

        //Initialize EditText
        nickNameEdit = findViewById(R.id.nickname_edit);
        nickNameEdit.setText(presetNick);

        // Set Buttons Listeners
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_game();
            }
        });
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_settings();
            }
        });
        buttonTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_tutorial();
            }
        });
        buttonBestScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_best_scores();
            }
        });
        buttonYourScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_your_scores();
            }
        });
    }

    /**
     * Starts the tutorial Activity
     */

    private void start_tutorial() {
        Intent tutorialActivity = new Intent(this, TutorialActivity.class);
        startActivity(tutorialActivity);
    }

    /**
     * Starts the settings Activity
     */
    private void start_settings() {
        // Get the user settings preferences and sends it to the Settings Activity
        Intent settingsActivity = new Intent(this, SettingsActivity.class);
        final boolean settings = getSharedPreferences(getString(R.string.musicSettings), MODE_PRIVATE).getBoolean(getString(R.string.musicSettings), true);
        settingsActivity.putExtra(getString(R.string.musicSettings), settings);
        startActivity(settingsActivity);
    }

    /**
     * Starts the game Activity
     */
    private void start_game() {
        int difficulty = getPreferences(MODE_PRIVATE).getInt(getString(R.string.difficulty), SharedConst.MEDIUM);

        // Get the content of the nickNameEdit
        playerName = nickNameEdit.getText().toString();
        // Saves the playerName preferences
        getPreferences(MODE_PRIVATE).edit().putString(getString(R.string.player_name), playerName).apply();

        // Starts the GameActivity and send it the difficulty and the playerName
        Intent gameActivity = new Intent(this, GameActivity.class);
        gameActivity.putExtra(getString(R.string.difficulty), difficulty);
        gameActivity.putExtra(getString(R.string.player_name), playerName);
        startActivity(gameActivity);
    }

    /**
     * Starts the Best Scores Activity
     */
    private void start_best_scores() {
        Intent scoresActivity = new Intent(this, ScoresActivity.class);
        startActivity(scoresActivity);
    }

    /**
     * Starts the Your Scores Activity
     */
    private void start_your_scores() {
        Intent scoresActivity = new Intent(this, ScoresActivity.class);
        // Get the content of the nickNameEdit
        playerName = nickNameEdit.getText().toString();
        // Saves the playerName preferences
        getPreferences(MODE_PRIVATE).edit().putString(getString(R.string.player_name), playerName).apply();

        // Starts the ScoresActivity and sends it the playerName
        scoresActivity.putExtra(getString(R.string.player_name), playerName);
        startActivity(scoresActivity);
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
        final boolean settings = getSharedPreferences(getString(R.string.musicSettings), MODE_PRIVATE).getBoolean(getString(R.string.musicSettings), true);
        MusicSingleton.getInstance(getApplicationContext()).changeMusicState(settings);
        UtilitiesActivity.hideStatusBar(this);
    }
}
