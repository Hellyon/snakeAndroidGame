package views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import snake.snake.R;
import tools.MusicSingleton;
import tools.SharedConst;
import tools.UtilitiesActivity;

/**
 * SettingsActivity
 *
 * @author Ilyace Benjelloun
 * @author Alex Cardoso
 */
public class SettingsActivity extends AppCompatActivity {
    /**
     * Initialize the SettingsActivity
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_activity);

        // Hides status bar
        tools.UtilitiesActivity.hideStatusBar(this);

        // Restart the music according to user choice
        MusicSingleton.getInstance(getApplicationContext()).start();

        // Hides status bar
        tools.UtilitiesActivity.hideStatusBar(this);

        // Gets the state of the settings, true by default
        Intent intent = getIntent();
        final boolean checked = intent.getBooleanExtra(getString(R.string.musicSettings), true);

        // Buttons
        final ToggleButton toggleMusic = findViewById(R.id.toggleButtonMusic);
        final RadioButton radioButtonEasy = findViewById(R.id.radioButton_Easy);
        final RadioButton radioButtonMedium = findViewById(R.id.radioButton_Medium);
        final RadioButton radioButtonHard = findViewById(R.id.radioButton_Hard);

        // Sets the ToggleButton to the right state depending on the state of the settings
        toggleMusic.setChecked(checked);

        // ToggleButton Listener
        toggleMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MusicSingleton.getInstance(getApplicationContext()).changeMusicState(SharedConst.ON);
                    getSharedPreferences(getString(R.string.musicSettings), MODE_PRIVATE).edit().putBoolean(getString(R.string.musicSettings), SharedConst.ON).apply();
                } else {
                    MusicSingleton.getInstance(getApplicationContext()).changeMusicState(SharedConst.OFF);
                    getSharedPreferences(getString(R.string.musicSettings), MODE_PRIVATE).edit().putBoolean(getString(R.string.musicSettings), SharedConst.OFF).apply();
                }
            }
        });

        // Easy RadioButton Listener
        radioButtonEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEasyMode();
            }
        });
        // Medium RadioButton Listener
        radioButtonMedium.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setMediumMode();
            }
        });
        // Hard RadioButton Listener
        radioButtonHard.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setHardMode();
            }
        });
    }


    /**
     * Display chosen difficulty
     */
    private void displayToast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    /**
     * Displays the selected difficulty (Easy) and save the choice of the User
     */
    private void setEasyMode() {
        displayToast(getString(R.string.Difficulty_text) + getString(R.string.EasyMode));

        getPreferences(MODE_PRIVATE).edit().putInt(getString(R.string.difficulty), SharedConst.EASY).apply();
    }

    /**
     * Displays the selected difficulty (Medium) and save the choice of the User
     */
    private void setMediumMode() {
        displayToast(getString(R.string.Difficulty_text) + getString(R.string.MediumMode));

        getPreferences(MODE_PRIVATE).edit().putInt(getString(R.string.difficulty), SharedConst.MEDIUM).apply();
    }

    /**
     * Displays the selected difficulty (Hard) and save the choice of the User
     */
    private void setHardMode() {
        displayToast(getString(R.string.Difficulty_text) + getString(R.string.HardMode));

        getPreferences(MODE_PRIVATE).edit().putInt(getString(R.string.difficulty), SharedConst.HARD).apply();
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
