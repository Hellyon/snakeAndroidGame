package tools;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Score;

/**
 * Handles the fireBase requests
 *
 * @author Ilyace Benjelloun
 */
public class DatabaseHandler {
    /**
     * Holds a reference to the database
     */
    private final DatabaseReference mDatabase;
    /**
     * Holds a reference to the global list of scores
     */
    private final List<Score> listScores;
    /**
     * Holds a reference to the player list of scores
     */
    private List<Score> listPlayerScores;
    /**
     * Holds a reference to the name of the player
     */
    private String playerNameListened;

    /**
     * Public Constructor
     */
    public DatabaseHandler() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // Initialize the lists of scores
        listScores = new ArrayList<>();
        listPlayerScores = new ArrayList<>();
        // No playerName Listened by default
        playerNameListened = "";
        // The global Database Queries
        Query scores = mDatabase.child("user-scores").orderByChild("score").limitToFirst(100);
        scores.addValueEventListener(createEventListener());
    }

    /**
     * Saves the player informations and his scores to the database for the leader boards only if his snakeLength is > 0
     *
     * @param score the instance of score
     */
    public void writeNewScore(Score score) {
        // Return if the player does not have eaten any pickups
        if (score.getSnakeLength() == SharedConst.BABY_SNAKE) return;
        // Generate a random key
        String key = mDatabase.child("user-scores").push().getKey();
        // Attach the score (value) to the generated key
        Map<String, Object> scoreValues = score.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        // Saves the score globally
        childUpdates.put("/user-scores/" + key, scoreValues);
        // Saves the score per Users
        childUpdates.put("/user/" + score.getPlayer() + "/" + key, scoreValues);
        // COMMIT !
        mDatabase.updateChildren(childUpdates);
    }

    /**
     * listScores Getter
     *
     * @return List<Score> the global list of scores
     */
    public List<Score> getScores() {
        return listScores;
    }

    /**
     * playerScores Getter
     * Removes the old ValueEventListener if necessary and then create a new One
     *
     * @param playerName the playerName to listen
     * @return List<Score> the player list of scores
     */
    public List<Score> getPlayerScores(final String playerName) {
        if (!playerNameListened.equals(playerName)) {
            // change the playerNameListened to the new one
            playerNameListened = playerName;
            // Read  the 1st 100 scores on another child
            // The Player Database Queries
            Query playerScores = mDatabase.child("/user/" + playerNameListened + "/").orderByChild("score").limitToFirst(100);
            // Create a new list and attach the ValueEventListener
            listPlayerScores = new ArrayList<>();
            playerScores.addValueEventListener(createPlayerEventListener());
        }
        return listPlayerScores;
    }

    /**
     * create the ValueEventListener for the global scores
     *
     * @return ValueEventListener, the newly ValueEventListener Created
     */
    private ValueEventListener createEventListener() {

        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot scoreSnapshot : dataSnapshot.getChildren()) {
                    // Create a new Score and adds it the list
                    Score score = scoreSnapshot.getValue(Score.class);
                    // the score is reversed for the LeaderBoard
                    score.reverseScore();
                    listScores.add(score);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("cancelled", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
    }

    /**
     * create the ValueEventListener for the player scores
     *
     * @return ValueEventListener, the newly ValueEventListener Created
     */
    private ValueEventListener createPlayerEventListener() {

        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot scoreSnapshot : dataSnapshot.getChildren()) {
                    // Create a new Score and adds it the list
                    Score score = scoreSnapshot.getValue(Score.class);
                    // the score is reversed for the LeaderBoard
                    score.reverseScore();
                    listPlayerScores.add(score);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("cancelled", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
    }
}
