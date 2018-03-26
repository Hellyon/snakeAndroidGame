package tools;

import android.content.Context;
import android.media.MediaPlayer;

import snake.snake.R;

/**
 * Used to play Music in App
 *
 * @author Ilyace Benjelloun
 * @author Alex Cardoso
 */
public class MusicSingleton {
    /**
     * The MediaPlayer
     */
    private final MediaPlayer mediaPlayer;
    /**
     * Does the user wants the music to be played ?
     */
    private boolean settings;
    /**
     * Used to set music looping to true
     */
    private final static boolean LOOP = true;

    /**
     * Private Constructor of MusicSingleton to prevent multiple instances
     *
     * @param context Context of the application
     */
    private MusicSingleton(Context context) {
        mediaPlayer = MediaPlayer.create(context, R.raw.music);
        mediaPlayer.setLooping(LOOP);
    }

    /**
     * Unique instance of mediaPlayer uninitialized
     */
    private static MusicSingleton INSTANCE = null;

    /**
     * Unique access point for mediaPlayer Instance
     *
     * @param context context of the application
     * @return MusicSingleton
     */
    public static synchronized MusicSingleton getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new MusicSingleton(context);
        }
        return INSTANCE;
    }

    /**
     * Settings Setter
     *
     * @param settings boolean
     */
    private void setSettings(boolean settings) {
        this.settings = settings;
    }

    public void changeMusicState(boolean checked) {
        setSettings(checked);
        swap();
    }

    /**
     * Starts the music
     */
    public void start() {
        if (settings) {
            mediaPlayer.start();
        }
    }

    /**
     * Stops the music
     */
    public void pause() {
        mediaPlayer.pause();
    }

    /**
     * Starts or stops the music depending of settings
     */
    private void swap() {
        if (settings) {
            mediaPlayer.start();
        } else {
            mediaPlayer.pause();
        }
    }
}
