package views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import model.Score;
import snake.snake.R;
import tools.SharedConst;

/**
 * Used to display scores in a ListView
 *
 * @author Ilyace Benjelloun
 */
class ScoreAdapter extends ArrayAdapter<Score> {
    /**
     * public Constructor
     *
     * @param context Context
     * @param scores  List<Score>
     */
    public ScoreAdapter(Context context, List<Score> scores) {
        super(context, 0, scores);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.player_score, parent, false);
        }
        // Initialize ScoreHolder if the viewHolder is null
        ScoreHolder viewHolder = (ScoreHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ScoreHolder();
            viewHolder.player = convertView.findViewById(R.id.player);
            viewHolder.difficulty = convertView.findViewById(R.id.difficulty);
            viewHolder.score = convertView.findViewById(R.id.score);
            viewHolder.snakeLength = convertView.findViewById(R.id.snakeLength);
            convertView.setTag(viewHolder);
        }
        // Get the score
        Score score = getItem(position);
        // Sets the message displayed
        viewHolder.player.setText(score.getPlayer());
        switch (score.getDifficulty()) {
            case SharedConst.EASY:
                viewHolder.difficulty.setText(R.string.EasyMode);
                break;
            case SharedConst.MEDIUM:
                viewHolder.difficulty.setText(R.string.MediumMode);
                break;
            case SharedConst.HARD:
                viewHolder.difficulty.setText(R.string.HardMode);
                break;
            default:
                viewHolder.difficulty.setText(R.string.unknown);
                break;
        }
        viewHolder.snakeLength.setText(getContext().getString(R.string.length, score.getSnakeLength()));
        viewHolder.score.setText(getContext().getString(R.string.score, score.getScore()));

        return convertView;
    }

    /**
     * Private ScoreHolder class
     */
    private class ScoreHolder {
        TextView player;
        TextView score;
        TextView snakeLength;
        TextView difficulty;
    }
}

